package org.ecside.table.cell;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.TableModelUtils;
import org.ecside.core.bean.Column;
import org.ecside.util.HtmlBuilder;

public class HeaderCell implements Cell {
	public String getExportDisplay(TableModel model, Column column) {
		return column.getTitle();
	}

	public String getHtmlDisplay(TableModel model, Column column) {
		HtmlBuilder html = new HtmlBuilder();

		String headerClass = column.getHeaderClass();
		String sortDivCss = null;
		String sortOrder = null;
		if (TableModelUtils.isSorted(model, column.getAlias())) {
			sortOrder = model.getLimit().getSort().getSortOrder();
			if (sortOrder.equals("default")) {
				sortOrder = "asc";
			} else if (sortOrder.equals("asc")) {
				sortDivCss = "sortASC";
				sortOrder = "desc";
			} else if (sortOrder.equals("desc")) {
				sortDivCss = "sortDESC";
				sortOrder = "default";
			}
		} else {
			sortOrder = "asc";
		}
		buildHeaderHtml(html, model, column, headerClass, sortDivCss, sortOrder);

		return html.toString();
	}

	public void buildHeaderHtml(HtmlBuilder html, TableModel model,
			Column column, String headerClass, String sortDivCss,
			String sortOrder) {
		html.append(buildHeaderHtml(model, column, null, headerClass,
				sortDivCss, sortOrder));
	}

	public static String buildHeaderHtml(TableModel model, Column column,
			String content, String headerClass, String sortDivCss,
			String sortOrder) {
		HtmlBuilder html = new HtmlBuilder();
		if (content == null) {
			content = column.getTitle();
		}
		boolean canResizeColWidth = (model.getTable().isResizeColWidth())
				&& (column.isResizeColWidth());

		int spanNum = column.getHeaderSpan();
		String display = "";
		String span = "";
		if (spanNum == 0) {
			return "";
		}
		if (spanNum > 0) {
			span = " colspan=\"" + spanNum + "\" ";
		} else {
			span = "";
		}
		html.td(2).valign("middle").append(" columnName=\"").append(
				column.getAlias()).append("\" ");
		if ("true".equalsIgnoreCase(column.getGroup())) {
			html.append(" group=\"true\" ");
		}
		if (column.isSortable()) {
			html.append(" sortable=\"true\" ");
		}
		if (canResizeColWidth) {
			html.append(" resizeColWidth=\"true\" ");
		}
		if (StringUtils.isNotBlank(column.getEditTemplate())) {
			html.append(" editTemplate=\"" + column.getEditTemplate() + "\" ");
		}
		if (StringUtils.isNotBlank(column.getIsDateField())) {
			html.append(" isDateField=\"" + column.getIsDateField() + "\" ");
		}
		int minWidth = column.getMinWidth();
		if ((canResizeColWidth) && (minWidth > 0)) {
			html.append(" minWidth=\"" + minWidth + "\" ");
		}
		if (StringUtils.isNotEmpty(column.getWidth())) {
			html.width(column.getWidth());
		}
		StringBuffer styleClass = new StringBuffer();
		if (StringUtils.isNotEmpty(headerClass)) {
			styleClass.append(headerClass);
		}
		if (StringUtils.isNotEmpty(column.getHeaderStyleClass())) {
			styleClass.append(" ").append(column.getHeaderStyleClass());
		}
		if (canResizeColWidth) {
			if (column.isLastColumn()) {
				styleClass.append(" ").append(" tableLastResizeableHeader");
			} else {
				styleClass.append(" ").append(" tableResizeableHeader");
			}
		}
		if (column.isEditable()) {
			styleClass.append(" ").append("editableColumn");
		}
		String tableId = model.getTable().getTableId();

		html.styleClass(styleClass.toString().trim());
		html.onmouseover("ECSideUtil.lightHeader(this,'" + tableId + "');");
		html.onmouseout("ECSideUtil.unlightHeader(this,'" + tableId + "');");

		html.append(" oncontextmenu=\"ECSideUtil.showColmunMenu(event,this,'"
				+ tableId + "');\" ");
		if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
			html.style(column.getHeaderStyle() + display);
		}
		if (column.isSortable()) {
			html.append(" onmouseup=\"");

			html.append(getSortAction(model, column, sortOrder)).append("\" ");

			boolean showTooltips = model.getTable().isShowTooltips();
			if (showTooltips) {
				String headercellTooltip = model.getMessages().getMessage(
						"column.headercell.tooltip.sort");
				html.title(headercellTooltip + " " + column.getTitle());
			}
		}
		html.append(span);
		html.close();

		html.span();
		String cstyle = "columnSeparator";
		if (canResizeColWidth) {
			html.append(" onmousedown=\"ECSideUtil.StartResize(event,this,'"
					+ tableId + "');\" onmouseup=\"" + "ECSideUtil"
					+ ".EndResize(event);\"");
			cstyle = cstyle + " columnResizeableSeparator";
		}
		html.styleClass(cstyle);
		html.close().append("&#160;");
		html.spanEnd();

		html.div().styleClass("headerTitle").close();
		html.append(content);
		if ((column.isSortable()) && (StringUtils.isNotEmpty(sortDivCss))) {
			html.nbsp();
			html.div();
			html.styleClass(sortDivCss);
			html.close();
			html.divEnd();
		}
		html.divEnd();

		html.tdEnd();

		return html.toString();
	}

	public static String getSortAction(TableModel model, Column column,
			String sortOrder) {
		String formid = model.getTable().getTableId();
		StringBuffer action = new StringBuffer("ECSideUtil.doSort(event,");
		action.append("'").append(column.getAlias()).append("',");
		action.append("'").append(sortOrder).append("',");
		action.append("'").append(formid).append("'");

		action.append(");");

		return action.toString();
	}
}
