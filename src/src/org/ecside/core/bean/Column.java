package org.ecside.core.bean;

import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.preferences.TableProperties;

@SuppressWarnings("serial")
public class Column extends BaseBean {
	private String alias;
	private String[] calc;
	private String[] calcTitle;
	private String cell;
	private String cellDisplay;
	private Object filterOptions;
	private Boolean escapeAutoFormat;
	private Boolean filterable;
	private String filterCell;
	private String filterClass;
	private String filterStyle;
	private String format;
	private String headerCell;
	private String headerClass;
	private String headerStyle;
	private String headerStyleClass;
	private String interceptor;
	private String parse;
	private String property;
	private Object propertyValue;
	private Boolean sortable;
	private String style;
	private String styleClass;
	private String title;
	private Object value;
	private String[] viewsAllowed;
	private String[] viewsDenied;
	private String width;
	private Boolean resizeColWidth;
	private int minWidth;
	private String tipTitle;
	private Object preCellValue;
	private String nowrap;
	private boolean isFirstColumn;
	private boolean isLastColumn;
	private String onclick;
	private String ondblclick;
	private String onmouseover;
	private String onmouseout;
	private int headerSpan = -1;
	private int calcSpan;
	private String cellValue;
	private Boolean ellipsis = null;
	private String editTemplate = null;
	private String editEvent = null;
	private Boolean editable;
	private String group;
	private int rowspan;
	private String isDateField = null;

	public Column(TableModel model) {
		setModel(model);
		this.rowspan = 1;
	}

	public void defaults() {
		this.cell = ColumnDefaults.getCell(this.model, this.cell);

		this.alias = ColumnDefaults.getAlias(this.alias, this.property);
		this.calcTitle = ColumnDefaults
				.getCalcTitle(this.model, this.calcTitle);
		this.escapeAutoFormat = ColumnDefaults.isEscapeAutoFormat(this.model,
				this.escapeAutoFormat);
		this.format = ColumnDefaults.getFormat(this.model, this, this.format);
		this.filterable = ColumnDefaults.isFilterable(this.model,
				this.filterable);
		this.filterCell = ColumnDefaults.getFilterCell(this.model,
				this.filterCell);
		this.filterOptions = ColumnDefaults.getFilterOptions(this.model,
				this.filterOptions);
		this.headerCell = ColumnDefaults.getHeaderCell(this.model,
				this.headerCell);
		this.headerClass = ColumnDefaults.getHeaderClass(this.model,
				this.headerClass);
		this.parse = ColumnDefaults.getParse(this.model, this, this.parse);
		this.sortable = ColumnDefaults.isSortable(this.model, this.sortable);
		this.title = ColumnDefaults
				.getTitle(this.model, this.title, this.alias);

		this.editEvent = TableProperties.getDefaultPreference(this.model,
				"column.editEvent", this.editEvent);
		this.editTemplate = TableProperties.getDefaultPreference(this.model,
				"column.editTemplate", this.editTemplate);
		this.editable = ColumnDefaults.isEditable(this.model, this.editable);

		this.resizeColWidth = ColumnDefaults.resizeColWidth(this.model,
				this.resizeColWidth);
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isCalculated() {
		return (this.calc != null) && (this.calc.length > 0);
	}

	public String[] getCalc() {
		return this.calc;
	}

	public void setCalc(String calc) {
		if (calc != null) {
			this.calc = StringUtils.split(calc, ",");
		}
	}

	public String[] getCalcTitle() {
		return this.calcTitle;
	}

	public void setCalcTitle(String calcTitle) {
		if (calcTitle != null) {
			this.calcTitle = StringUtils.split(calcTitle, ",");
		}
	}

	public String getCell() {
		return this.cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getCellDisplay() {
		return this.cellDisplay;
	}

	public void setCellDisplay(String cellDisplay) {
		this.cellDisplay = cellDisplay;
	}

	@SuppressWarnings("unchecked")
	public Collection getFilterOptions() {
		return (Collection) this.filterOptions;
	}

	public void setFilterOptions(Object filterOptions) {
		this.filterOptions = filterOptions;
	}

	public boolean isEscapeAutoFormat() {
		return this.escapeAutoFormat.booleanValue();
	}

	public void setEscapeAutoFormat(Boolean escapeAutoFormat) {
		this.escapeAutoFormat = escapeAutoFormat;
	}

	public boolean isFilterable() {
		return this.filterable.booleanValue();
	}

	public void setFilterable(Boolean filterable) {
		this.filterable = filterable;
	}

	public String getFilterCell() {
		return this.filterCell;
	}

	public void setFilterCell(String filterCell) {
		this.filterCell = filterCell;
	}

	public String getFilterClass() {
		return this.filterClass;
	}

	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}

	public String getFilterStyle() {
		return this.filterStyle;
	}

	public void setFilterStyle(String filterStyle) {
		this.filterStyle = filterStyle;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getHeaderCell() {
		return this.headerCell;
	}

	public void setHeaderCell(String headerCell) {
		this.headerCell = headerCell;
	}

	public String getHeaderClass() {
		return this.headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public String getHeaderStyle() {
		return this.headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getInterceptor() {
		return this.interceptor;
	}

	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}

	public String getParse() {
		return this.parse;
	}

	public void setParse(String parse) {
		this.parse = parse;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getPropertyValue() {
		return this.propertyValue;
	}

	public String getPropertyValueAsString() {
		Object value = getPropertyValue();
		if (value != null) {
			return String.valueOf(value);
		}
		return "";
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public boolean isSortable() {
		return this.sortable.booleanValue();
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyleClass() {
		return this.styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getValue() {
		return this.value;
	}

	public String getValueAsString() {
		Object value = getValue();
		if (value != null) {
			return String.valueOf(value);
		}
		return "";
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String[] getViewsAllowed() {
		return this.viewsAllowed;
	}

	public void setViewsAllowed(String viewsAllowed) {
		if (viewsAllowed != null) {
			this.viewsAllowed = StringUtils.split(viewsAllowed, ",");
		}
	}

	public String[] getViewsDenied() {
		return this.viewsDenied;
	}

	public void setViewsDenied(String viewsDenied) {
		if (viewsDenied != null) {
			this.viewsDenied = StringUtils.split(viewsDenied, ",");
		}
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isDate() {
		if ((StringUtils.isNotEmpty(getCell()))
				&& (getCell().equals(this.model.getPreferences().getPreference(
						"column.cell.date")))) {
			return true;
		}
		return false;
	}

	public boolean isCurrency() {
		if ((StringUtils.isNotBlank(getCell()))
				&& (getCell().equalsIgnoreCase(this.model.getPreferences()
						.getPreference("column.cell.currency")))) {
			return true;
		}
		return false;
	}

	public boolean isFirstColumn() {
		return this.isFirstColumn;
	}

	public void setFirstColumn(boolean isFirstColumn) {
		this.isFirstColumn = isFirstColumn;
	}

	public boolean isLastColumn() {
		return this.isLastColumn;
	}

	public void setLastColumn(boolean isLastColumn) {
		this.isLastColumn = isLastColumn;
	}

	public int getHeaderSpan() {
		return this.headerSpan;
	}

	public void setHeaderSpan(int headerSpan) {
		this.headerSpan = headerSpan;
	}

	public String getOnclick() {
		return this.onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return this.ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public int getCalcSpan() {
		return this.calcSpan;
	}

	public void setCalcSpan(int calcSpan) {
		this.calcSpan = calcSpan;
	}

	public Boolean getEllipsis() {
		return this.ellipsis;
	}

	public void setEllipsis(Boolean ellipsis) {
		this.ellipsis = ellipsis;
	}

	public String getOnmouseout() {
		return this.onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return this.onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getCellValue() {
		return this.cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

	public int getMinWidth() {
		return this.minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public String getHeaderStyleClass() {
		return this.headerStyleClass;
	}

	public void setHeaderStyleClass(String headerStyleClass) {
		this.headerStyleClass = headerStyleClass;
	}

	public String getEditTemplate() {
		return this.editTemplate;
	}

	public void setEditTemplate(String editTemplate) {
		this.editTemplate = editTemplate;
	}

	public String getEditEvent() {
		return this.editEvent;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public String getNowrap() {
		return this.nowrap;
	}

	public void setNowrap(String nowrap) {
		this.nowrap = nowrap;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Boolean getResizeColWidth() {
		return this.resizeColWidth;
	}

	public boolean isResizeColWidth() {
		return this.resizeColWidth == null ? false : this.resizeColWidth
				.booleanValue();
	}

	public void setResizeColWidth(Boolean resizeColWidth) {
		this.resizeColWidth = resizeColWidth;
	}

	public int getRowspan() {
		return this.rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public void addStyle(String style) {
		if (StringUtils.isNotBlank(getStyle())) {
			setStyle(getStyle() + ";" + style);
		} else {
			setStyle(style);
		}
	}

	public void addStyleClass(String styleClass) {
		if (StringUtils.isNotBlank(getStyleClass())) {
			setStyleClass(getStyleClass() + " " + styleClass);
		} else {
			setStyleClass(styleClass);
		}
	}

	public Object getPreCellValue() {
		return this.preCellValue;
	}

	public void setPreCellValue(Object preCellValue) {
		this.preCellValue = preCellValue;
	}

	public Boolean getEditable() {
		return this.editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return this.editable == null ? false : this.editable.booleanValue();
	}

	public String getTipTitle() {
		return this.tipTitle;
	}

	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}

	public String getIsDateField() {
		return isDateField;
	}

	public void setIsDateField(String isDateField) {
		this.isDateField = isDateField;
	}
}
