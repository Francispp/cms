var TemplateWizard = Component.extend(
{
	initialize : function ()
	{
		this.parent (null, null, "CMS_TEMPLATEWIZARD", "templateWizard", "400", "360", "模板向导"); 
		this.views = [];
		this.activateView = null;
		
		this.addEvent ("onHandlePage", function (editor)
		{
			$("nextStepButton").addEvent ("click", function ()
			{
				if (this.activateView)
				{
					var nextView = this.activateView.id + ":" + this.activateView.value;
					
					this.activateView (nextView);
				}
			}.bind (this));
			
			$("prevStepButton").addEvent ("onclick") = function ()
			{
				if (this.activateView)
				{
					if (this.activateView.id.contains (":"))
					{
						var prevView = this.activateView.id.substring (0, this.activateView.id.lastIndexOf (":") - 1);
						
						this.activateView (prevView);
					}
				}
			}.bind (this));
		});
	},
	
	addView : function (view)
	{
		if (!this.views.contains ($(view)))
		{
			$(view).value = "next";
			
			this.views.push ($(view));
		}
	},
	
	activateView : function (view)
	{
		if (this.views.contains ($(view)))
		{
			this.activateView = $(view);
		}
	}
});