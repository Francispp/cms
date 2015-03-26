if (FCKBrowserInfo.IsIE)
{
	FCKXHtml._AppendAttributes = function( xmlNode, htmlNode, node, nodeName )
	{
		var aAttributes = htmlNode.attributes ;
	
		for ( var n = 0 ; n < aAttributes.length ; n++ )
		{
			var oAttribute = aAttributes[n] ;
	
			if ( oAttribute.specified )
			{
				var sAttName = oAttribute.nodeName;
				var sAttValue ;
	
				// Ignore any attribute starting with "_fck".
				if ( sAttName.StartsWith( '_fck' ) )
					continue ;
				// The following must be done because of a bug on IE regarding the style
				// attribute. It returns "null" for the nodeValue.
				else if ( sAttName == 'style' )
				{
					var data = FCKTools.ProtectFormStyles( htmlNode ) ;
					sAttValue = htmlNode.style.cssText.replace( FCKRegexLib.StyleProperties, FCKTools.ToLowerCase ) ;
					FCKTools.RestoreFormStyles( htmlNode, data ) ;
				}
				// There are two cases when the oAttribute.nodeValue must be used:
				//		- for the "class" attribute
				//		- for events attributes (on IE only).
				else if ( sAttName == 'class' )
				{
					sAttValue = oAttribute.nodeValue.replace( FCKRegexLib.FCK_Class, '' ) ;
					if ( sAttValue.length == 0 )
						continue ;
				}
				else if ( sAttName.indexOf('on') == 0 )
					sAttValue = oAttribute.nodeValue ;
				else if ( nodeName == 'body' && sAttName == 'contenteditable' )
					continue ;
				// XHTML doens't support attribute minimization like "CHECKED". It must be transformed to checked="checked".
				else if ( oAttribute.nodeValue === true )
					sAttValue = sAttName ;
				else
				{
					// We must use getAttribute to get it exactly as it is defined.
					// There are some rare cases that IE throws an error here, so we must try/catch.
					try
					{
						sAttValue = htmlNode.getAttribute( sAttName, 2 ) ;
					}
					catch (e) {}
				}
				this._AppendAttribute( node, sAttName, sAttValue || oAttribute.nodeValue ) ;
			}
		}
	}
}
else
{
	FCKXHtml._AppendAttributes = function( xmlNode, htmlNode, node )
	{
		var aAttributes = htmlNode.attributes ;
	
		for ( var n = 0 ; n < aAttributes.length ; n++ )
		{
			var oAttribute = aAttributes[n] ;
	
			if ( oAttribute.specified )
			{
				var sAttName = oAttribute.nodeName;
				var sAttValue ;
	
				// Ignore any attribute starting with "_fck".
				if ( sAttName.StartsWith( '_fck' ) )
					continue ;
				// There is a bug in Mozilla that returns '_moz_xxx' attributes as specified.
				else if ( sAttName.indexOf( '_moz' ) == 0 )
					continue ;
				// There are one cases (on Gecko) when the oAttribute.nodeValue must be used:
				//		- for the "class" attribute
				else if ( sAttName == 'class' )
				{
					sAttValue = oAttribute.nodeValue.replace( FCKRegexLib.FCK_Class, '' ) ;
					if ( sAttValue.length == 0 )
						continue ;
				}
				// XHTML doens't support attribute minimization like "CHECKED". It must be transformed to checked="checked".
				else if ( oAttribute.nodeValue === true )
					sAttValue = sAttName ;
				else
					sAttValue = htmlNode.getAttribute( sAttName, 2 ) ;	// We must use getAttribute to get it exactly as it is defined.
	
				this._AppendAttribute( node, sAttName, sAttValue ) ;
			} 
		}
	}
}

FCK.Preview = function()
{
	var sHTML ;

	if ( FCKConfig.FullPage )
	{
		if ( FCK.TempBaseTag.length > 0 )
			sHTML = FCK.TempBaseTag + FCK.GetXHTML() ;
		else
			sHTML = FCK.GetXHTML() ;
	}
	else
	{
		sHTML =
			FCKConfig.DocType +
			'<html dir="' + FCKConfig.ContentLangDirection + '">' +
			'<head>' +
			FCK.TempBaseTag +
			'<title>' + FCKLang.Preview + '</title>' +
			_FCK_GetEditorAreaStyleTags() +
			'</head><body' + FCKConfig.GetBodyAttributes() + '>' +
			FCK.GetXHTML() +
			'</body></html>' ; 
	}

	sHTML = '<html><body style="display:none"><form id="frm" action="/cms/template!preview.action" method="post">' + 
		'<input type="hidden" name="domain.type" value="' + parent.document.getElementById ('domain_type').value + '"/>' +  
		'<textarea id="domain.body" name="domain.body">' + escape (sHTML) + '</textarea></form></body></html>' + 
		'<script type="text/javascript">document.getElementById ("domain.body").value = unescape (document.getElementById ("domain.body").value); frm.submit();</script>'
		
	var iWidth	= FCKConfig.ScreenWidth * 0.8 ;
	var iHeight	= FCKConfig.ScreenHeight * 0.7 ;
	var iLeft	= ( FCKConfig.ScreenWidth - iWidth ) / 2 ;

	var sOpenUrl = '' ;
	if ( FCK_IS_CUSTOM_DOMAIN && FCKBrowserInfo.IsIE)
	{
		window._FCKHtmlToLoad = sHTML ;
		sOpenUrl = 'javascript:void( (function(){' +
			'document.open() ;' +
			'document.domain="' + document.domain + '" ;' +
			'document.write( window.opener._FCKHtmlToLoad );' +
			'document.close() ;' +
			'window.opener._FCKHtmlToLoad = null ;' +
			'})() )' ;
	}

	var oWindow = window.open( sOpenUrl, null, 'toolbar=yes,location=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=' + iWidth + ',height=' + iHeight + ',left=' + iLeft ) ;

	if ( !FCK_IS_CUSTOM_DOMAIN || !FCKBrowserInfo.IsIE)
	{	
		oWindow.document.write( sHTML );
		oWindow.document.close();
	}
}

FCKXHtml.TagProcessors =
{
	a : function( node, htmlNode )
	{
		// Firefox may create empty tags when deleting the selection in some special cases (SF-BUG 1556878).
		if ( htmlNode.innerHTML.Trim().length == 0 && !htmlNode.name )
			return false ;

		var sSavedUrl = htmlNode.getAttribute( '_fcksavedurl' ) ;
		if ( sSavedUrl != null )
			FCKXHtml._AppendAttribute( node, 'href', sSavedUrl ) ;


		// Anchors with content has been marked with an additional class, now we must remove it.
		if ( FCKBrowserInfo.IsIE )
		{
			// Buggy IE, doesn't copy the name of changed anchors.
			if ( htmlNode.name )
				FCKXHtml._AppendAttribute( node, 'name', htmlNode.name ) ;
		}

		node = FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;

		return node ;
	},

	area : function( node, htmlNode )
	{
		var sSavedUrl = htmlNode.getAttribute( '_fcksavedurl' ) ;
		if ( sSavedUrl != null )
			FCKXHtml._AppendAttribute( node, 'href', sSavedUrl ) ;

		// IE ignores the "COORDS" and "SHAPE" attribute so we must add it manually.
		if ( FCKBrowserInfo.IsIE )
		{
			if ( ! node.attributes.getNamedItem( 'coords' ) )
			{
				var sCoords = htmlNode.getAttribute( 'coords', 2 ) ;
				if ( sCoords && sCoords != '0,0,0' )
					FCKXHtml._AppendAttribute( node, 'coords', sCoords ) ;
			}

			if ( ! node.attributes.getNamedItem( 'shape' ) )
			{
				var sShape = htmlNode.getAttribute( 'shape', 2 ) ;
				if ( sShape && sShape.length > 0 )
					FCKXHtml._AppendAttribute( node, 'shape', sShape.toLowerCase() ) ;
			}
		}

		return node ;
	},

	body : function( node, htmlNode )
	{
		node = FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;
		// Remove spellchecker attributes added for Firefox when converting to HTML code (Bug #1351).
		node.removeAttribute( 'spellcheck' ) ;
		return node ;
	},

	// IE loses contents of iframes, and Gecko does give it back HtmlEncoded
	// Note: Opera does lose the content and doesn't provide it in the innerHTML string
	iframe : function( node, htmlNode )
	{
		var sHtml = htmlNode.innerHTML ;

		// Gecko does give back the encoded html
		if ( FCKBrowserInfo.IsGecko )
			sHtml = FCKTools.HTMLDecode( sHtml );

		// Remove the saved urls here as the data won't be processed as nodes
		sHtml = sHtml.replace( /\s_fcksavedurl="[^"]*"/g, '' ) ;

		node.appendChild( FCKXHtml.XML.createTextNode( FCKXHtml._AppendSpecialItem( sHtml ) ) ) ;

		return node ;
	},

	img : function( node, htmlNode )
	{
		// The "ALT" attribute is required in XHTML.
		if ( ! node.attributes.getNamedItem( 'alt' ) )
			FCKXHtml._AppendAttribute( node, 'alt', '' ) ;

		var sSavedUrl = htmlNode.getAttribute( '_fcksavedurl' ) ;
		if ( sSavedUrl != null )
			FCKXHtml._AppendAttribute( node, 'src', sSavedUrl ) ;

		return node ;
	},

	// Fix orphaned <li> nodes (Bug #503).
	li : function( node, htmlNode, targetNode )
	{
		return FCKXHtml._AppendChildNodes( node, htmlNode, true ) ;
	},

	// Fix nested <ul> and <ol>.
	ol : function( node, htmlNode, targetNode )
	{
		if ( htmlNode.innerHTML.Trim().length == 0 )
			return false ;

		var ePSibling = targetNode.lastChild ;

		if ( ePSibling && ePSibling.nodeType == 3 )
			ePSibling = ePSibling.previousSibling ;

		if ( ePSibling && ePSibling.nodeName.toUpperCase() == 'LI' )
		{
			htmlNode._fckxhtmljob = null ;
			FCKXHtml._AppendNode( ePSibling, htmlNode ) ;
			return false ;
		}

		node = FCKXHtml._AppendChildNodes( node, htmlNode ) ;

		return node ;
	},

	pre : function ( node, htmlNode )
	{
		var firstChild = htmlNode.firstChild ;

		if ( firstChild && firstChild.nodeType == 3 )
			node.appendChild( FCKXHtml.XML.createTextNode( FCKXHtml._AppendSpecialItem( '\r\n' ) ) ) ;

		FCKXHtml._AppendChildNodes( node, htmlNode, true ) ;

		return node ;
	},

	script : function( node, htmlNode )
	{
		// The "TYPE" attribute is required in XHTML.
		if ( ! node.attributes.getNamedItem( 'type' ) )
			FCKXHtml._AppendAttribute( node, 'type', 'text/javascript' ) ;

		node.appendChild( FCKXHtml.XML.createTextNode( FCKXHtml._AppendSpecialItem( htmlNode.text ) ) ) ;

		return node ;
	},

	span : function( node, htmlNode )
	{
		// Firefox may create empty tags when deleting the selection in some special cases (SF-BUG 1084404).
		if ( htmlNode.innerHTML.length == 0 )
			return false ;

		node = FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;

		return node ;
	},

	style : function( node, htmlNode )
	{
		// The "TYPE" attribute is required in XHTML.
		if ( ! node.attributes.getNamedItem( 'type' ) )
			FCKXHtml._AppendAttribute( node, 'type', 'text/css' ) ;

		var cssText = htmlNode.innerHTML ;
		if ( FCKBrowserInfo.IsIE )	// Bug #403 : IE always appends a \r\n to the beginning of StyleNode.innerHTML
			cssText = cssText.replace( /^(\r\n|\n|\r)/, '' ) ;

		node.appendChild( FCKXHtml.XML.createTextNode( FCKXHtml._AppendSpecialItem( cssText ) ) ) ;

		return node ;
	},

	title : function( node, htmlNode )
	{
		node.appendChild( FCKXHtml.XML.createTextNode( FCK.EditorDocument.title ) ) ;

		return node ;
	}
} ;

FCKXHtml.TagProcessors.ul = FCKXHtml.TagProcessors.ol ;

FCKXHtml.TagProcessors['input'] = function( node, htmlNode )
{
	if ( htmlNode.name )
		FCKXHtml._AppendAttribute( node, 'name', htmlNode.name ) ;

	if ( htmlNode.value)
		FCKXHtml._AppendAttribute( node, 'value', htmlNode.value ) ;

	if ( !node.attributes.getNamedItem( 'type' ) )
		FCKXHtml._AppendAttribute( node, 'type', 'text' ) ;

	return node ;
}

FCKCommands.GetCommand = function( commandName )
{
	var oCommand = FCKCommands.LoadedCommands[ commandName ] ;

	if ( oCommand )
		return oCommand ;

	switch ( commandName )
	{
		case 'Bold'			:
		case 'Italic'		:
		case 'Underline'	:
		case 'StrikeThrough':
		case 'Subscript'	:
		case 'Superscript'	: oCommand = new FCKCoreStyleCommand( commandName ) ; break ;

		case 'RemoveFormat'	: oCommand = new FCKRemoveFormatCommand() ; break ;

		case 'DocProps'		: oCommand = new FCKDialogCommand( 'DocProps'	, FCKLang.DocProps				, 'dialog/fck_docprops.html'	, 400, 380, FCKCommands.GetFullPageState ) ; break ;
		case 'Templates'	: oCommand = new FCKDialogCommand( 'Templates'	, FCKLang.DlgTemplatesTitle		, 'dialog/fck_template.html'	, 380, 450 ) ; break ;
		case 'Link'			: oCommand = new FCKDialogCommand( 'Link'		, FCKLang.DlgLnkWindowTitle		, 'dialog/fck_link.html'		, 400, 300 ) ; break ;
		case 'Unlink'		: oCommand = new FCKUnlinkCommand() ; break ;
		case 'Anchor'		: oCommand = new FCKDialogCommand( 'Anchor'		, FCKLang.DlgAnchorTitle		, 'dialog/fck_anchor.html'		, 370, 160 ) ; break ;
		case 'AnchorDelete'	: oCommand = new FCKAnchorDeleteCommand() ; break ;
		case 'BulletedList'	: oCommand = new FCKDialogCommand( 'BulletedList', FCKLang.BulletedListProp		, 'dialog/fck_listprop.html?UL'	, 370, 160 ) ; break ;
		case 'NumberedList'	: oCommand = new FCKDialogCommand( 'NumberedList', FCKLang.NumberedListProp		, 'dialog/fck_listprop.html?OL'	, 370, 160 ) ; break ;
		case 'About'		: oCommand = new FCKDialogCommand( 'About'		, FCKLang.About					, 'dialog/fck_about.html'		, 420, 330, function(){ return FCK_TRISTATE_OFF ; } ) ; break ;
		case 'Find'			: oCommand = new FCKDialogCommand( 'Find'		, FCKLang.DlgFindAndReplaceTitle, 'dialog/fck_replace.html'		, 340, 230, null, null, 'Find' ) ; break ;
		case 'Replace'		: oCommand = new FCKDialogCommand( 'Replace'	, FCKLang.DlgFindAndReplaceTitle, 'dialog/fck_replace.html'		, 340, 230, null, null, 'Replace' ) ; break ;

		case 'Image'		: oCommand = new FCKDialogCommand( 'Image'		, FCKLang.DlgImgTitle			, 'dialog/fck_image.html'		, 450, 390 ) ; break ;
		case 'Flash'		: oCommand = new FCKDialogCommand( 'Flash'		, FCKLang.DlgFlashTitle			, 'dialog/fck_flash.html'		, 450, 390 ) ; break ;
		case 'SpecialChar'	: oCommand = new FCKDialogCommand( 'SpecialChar', FCKLang.DlgSpecialCharTitle	, 'dialog/fck_specialchar.html'	, 400, 290 ) ; break ;
		case 'Smiley'		: oCommand = new FCKDialogCommand( 'Smiley'		, FCKLang.DlgSmileyTitle		, 'dialog/fck_smiley.html'		, FCKConfig.SmileyWindowWidth, FCKConfig.SmileyWindowHeight ) ; break ;
		case 'Table'		: oCommand = new FCKDialogCommand( 'Table'		, FCKLang.DlgTableTitle			, 'dialog/fck_table.html'		, 480, 250 ) ; break ;
		case 'TableProp'	: oCommand = new FCKDialogCommand( 'Table'		, FCKLang.DlgTableTitle			, 'dialog/fck_table.html?Parent', 480, 250 ) ; break ;
		case 'TableCellProp': oCommand = new FCKDialogCommand( 'TableCell'	, FCKLang.DlgCellTitle			, 'dialog/fck_tablecell.html'	, 550, 240 ) ; break ;

		case 'Style'		: oCommand = new FCKStyleCommand() ; break ;

		case 'FontName'		: oCommand = new FCKFontNameCommand() ; break ;
		case 'FontSize'		: oCommand = new FCKFontSizeCommand() ; break ;
		case 'FontFormat'	: oCommand = new FCKFormatBlockCommand() ; break ;

		case 'Source'		: oCommand = new FCKSourceCommand() ; break ;
		case 'Preview'		: oCommand = new FCKPreviewCommand() ; break ;
		case 'Save'			: oCommand = new FCKSaveCommand() ; break ;
		case 'NewPage'		: oCommand = new FCKNewPageCommand() ; break ;
		case 'PageBreak'	: oCommand = new FCKPageBreakCommand() ; break ;
		case 'Rule'			: oCommand = new FCKRuleCommand() ; break ;

		case 'TextColor'	: oCommand = new FCKTextColorCommand('ForeColor') ; break ;
		case 'BGColor'		: oCommand = new FCKTextColorCommand('BackColor') ; break ;

		case 'Paste'		: oCommand = new FCKPasteCommand() ; break ;
		case 'PasteText'	: oCommand = new FCKPastePlainTextCommand() ; break ;
		case 'PasteWord'	: oCommand = new FCKPasteWordCommand() ; break ;

		case 'JustifyLeft'	: oCommand = new FCKJustifyCommand( 'left' ) ; break ;
		case 'JustifyCenter'	: oCommand = new FCKJustifyCommand( 'center' ) ; break ;
		case 'JustifyRight'	: oCommand = new FCKJustifyCommand( 'right' ) ; break ;
		case 'JustifyFull'	: oCommand = new FCKJustifyCommand( 'justify' ) ; break ;
		case 'Indent'	: oCommand = new FCKIndentCommand( 'indent', FCKConfig.IndentLength ) ; break ;
		case 'Outdent'	: oCommand = new FCKIndentCommand( 'outdent', FCKConfig.IndentLength * -1 ) ; break ;
		case 'Blockquote'	: oCommand = new FCKBlockQuoteCommand() ; break ;

		case 'TableInsertRowAfter'		: oCommand = new FCKTableCommand('TableInsertRowAfter') ; break ;
		case 'TableInsertRowBefore'		: oCommand = new FCKTableCommand('TableInsertRowBefore') ; break ;
		case 'TableDeleteRows'			: oCommand = new FCKTableCommand('TableDeleteRows') ; break ;
		case 'TableInsertColumnAfter'	: oCommand = new FCKTableCommand('TableInsertColumnAfter') ; break ;
		case 'TableInsertColumnBefore'	: oCommand = new FCKTableCommand('TableInsertColumnBefore') ; break ;
		case 'TableDeleteColumns'		: oCommand = new FCKTableCommand('TableDeleteColumns') ; break ;
		case 'TableInsertCellAfter'		: oCommand = new FCKTableCommand('TableInsertCellAfter') ; break ;
		case 'TableInsertCellBefore'	: oCommand = new FCKTableCommand('TableInsertCellBefore') ; break ;
		case 'TableDeleteCells'			: oCommand = new FCKTableCommand('TableDeleteCells') ; break ;
		case 'TableMergeCells'			: oCommand = new FCKTableCommand('TableMergeCells') ; break ;
		case 'TableMergeRight'			: oCommand = new FCKTableCommand('TableMergeRight') ; break ;
		case 'TableMergeDown'			: oCommand = new FCKTableCommand('TableMergeDown') ; break ;
		case 'TableHorizontalSplitCell'	: oCommand = new FCKTableCommand('TableHorizontalSplitCell') ; break ;
		case 'TableVerticalSplitCell'	: oCommand = new FCKTableCommand('TableVerticalSplitCell') ; break ;
		case 'TableDelete'				: oCommand = new FCKTableCommand('TableDelete') ; break ;

		case 'Form'			: oCommand = new FCKDialogCommand( 'Form'		, FCKLang.Form			, 'dialog/fck_form.html'		, 480, 210 ) ; break ;
		case 'Checkbox'		: oCommand = new FCKDialogCommand( 'Checkbox'	, FCKLang.Checkbox		, 'dialog/fck_checkbox.html'	, 380, 200 ) ; break ;
		case 'Radio'		: oCommand = new FCKDialogCommand( 'Radio'		, FCKLang.RadioButton	, 'dialog/fck_radiobutton.html'	, 380, 200 ) ; break ;
		case 'TextField'	: oCommand = new FCKDialogCommand( 'TextField'	, FCKLang.TextField		, 'dialog/fck_textfield.html'	, 380, 210 ) ; break ;
		case 'Textarea'		: oCommand = new FCKDialogCommand( 'Textarea'	, FCKLang.Textarea		, 'dialog/fck_textarea.html'	, 380, 210 ) ; break ;
		case 'HiddenField'	: oCommand = new FCKDialogCommand( 'HiddenField', FCKLang.HiddenField	, 'dialog/fck_hiddenfield.html'	, 380, 190 ) ; break ;
		case 'Button'		: oCommand = new FCKDialogCommand( 'Button'		, FCKLang.Button		, 'dialog/fck_button.html'		, 380, 210 ) ; break ;
		case 'Select'		: oCommand = new FCKDialogCommand( 'Select'		, FCKLang.SelectionField, 'dialog/fck_select.html'		, 400, 340 ) ; break ;
		case 'ImageButton'	: oCommand = new FCKDialogCommand( 'ImageButton', FCKLang.ImageButton	, 'dialog/fck_image.html?ImageButton', 450, 390 ) ; break ;

		case 'SpellCheck'	: oCommand = new FCKSpellCheckCommand() ; break ;
		case 'FitWindow'	: oCommand = new FCKFitWindow() ; break ;

		case 'Undo'	: oCommand = new FCKUndoCommand() ; break ;
		case 'Redo'	: oCommand = new FCKRedoCommand() ; break ;
		case 'Copy'	: oCommand = new FCKCutCopyCommand( false ) ; break ;
		case 'Cut'	: oCommand = new FCKCutCopyCommand( true ) ; break ;

		case 'SelectAll'			: oCommand = new FCKSelectAllCommand() ; break ;
		case 'InsertOrderedList'	: oCommand = new FCKListCommand( 'insertorderedlist', 'ol' ) ; break ;
		case 'InsertUnorderedList'	: oCommand = new FCKListCommand( 'insertunorderedlist', 'ul' ) ; break ;
		case 'ShowBlocks' : oCommand = new FCKShowBlockCommand( 'ShowBlocks', FCKConfig.StartupShowBlocks ? FCK_TRISTATE_ON : FCK_TRISTATE_OFF ) ; break ;

		// Generic Undefined command (usually used when a command is under development).
		case 'Undefined'	: oCommand = new FCKUndefinedCommand() ; break ;

		// By default we assume that it is a named command.
		default:
			if ( FCKRegexLib.NamedCommands.test( commandName ) )
				oCommand = new FCKNamedCommand( commandName ) ;
			else
			{
				alert( FCKLang.UnknownCommand.replace( /%1/g, commandName ) ) ;
				return null ;
			}
	}

	FCKCommands.LoadedCommands[ commandName ] = oCommand ;

	return oCommand ;
}

FCK.RegisterDoubleClickHandler (function (element)
{
	if (element.tagName.toLowerCase () == "form")
	{
		FCKCommands.GetCommand ("Form").Execute ();
	}
},"FORM");
		
ScriptLoader = new Object ();
ScriptLoader.basepath = FCKConfig.PluginsPath + "cms/"
ScriptLoader.load = function (scriptName)
{
	document.write("<script type='text/javascript' src='" + ScriptLoader.basepath + scriptName + "'><\/script>") ;
}

ScriptLoader.load ("../../../../mootools.js");
ScriptLoader.load ("../../dialog/common/fck_dialog_common.js");
ScriptLoader.load ("component.js");
ScriptLoader.load ("js/checkbox.js");
ScriptLoader.load ("js/radio.js");
ScriptLoader.load ("js/textField.js");
ScriptLoader.load ("js/textArea.js");
ScriptLoader.load ("js/select.js");
ScriptLoader.load ("js/button.js");
ScriptLoader.load ("js/property.js");
ScriptLoader.load ("js/list.js");
ScriptLoader.load ("js/html.js");
ScriptLoader.load ("js/word.js");
ScriptLoader.load ("js/excel.js");
ScriptLoader.load ("js/ppt.js");
ScriptLoader.load ("js/beanShellScript.js");
ScriptLoader.load ("js/mediaPlayer.js");
ScriptLoader.load ("js/include.js");
ScriptLoader.load ("js/list.js");
ScriptLoader.load ("js/upload.js");
ScriptLoader.load ("js/download.js");
ScriptLoader.load ("js/photo.js");
ScriptLoader.load ("js/but.js");
ScriptLoader.load ("js/navigation.js");
ScriptLoader.load ("js/menu.js");
ScriptLoader.load ("js/mediaUpload.js");
ScriptLoader.load ("js/mediaView.js");
ScriptLoader.load ("js/homelist.js");
ScriptLoader.load ("js/homewaplist.js");
ScriptLoader.load ("js/staticlist.js");
ScriptLoader.load ("js/user.js");
ScriptLoader.load ("js/message.js");
ScriptLoader.load ("js/attach.js");
ScriptLoader.load ("js/clickcount.js");
ScriptLoader.load ("js/templateInclude.js");
ScriptLoader.load ("js/email.js");
ScriptLoader.load ("js/rss.js");
ScriptLoader.load ("js/login.js");
ScriptLoader.load ("js/register.js");
ScriptLoader.load ("js/advertisement.js");
ScriptLoader.load ("js/datePicker.js");
ScriptLoader.load ("js/search.js");
ScriptLoader.load ("js/history.js");
ScriptLoader.load ("js/flow.js");
ScriptLoader.load ("js/tree.js");
ScriptLoader.load ("js/operation.js");
/*ScriptLoader.load ("js/beanShellScript.js");
ScriptLoader.load ("js/templateWizard.js");
ScriptLoader.load ("js/documentLoop.js");
ScriptLoader.load ("js/userPicker.js");*/
ScriptLoader.load ("register.js");
