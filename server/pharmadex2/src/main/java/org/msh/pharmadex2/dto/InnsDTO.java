package org.msh.pharmadex2.dto;

import org.msh.pdex2.dto.table.TableQtb;
import org.msh.pharmadex2.dto.form.FormFieldDTO;

public class InnsDTO {

	private String title = "Inns";
	private TableQtb table = new TableQtb();
	private TableQtb selectedtable = new TableQtb();
	
	private long id = 0l;
	private FormFieldDTO<String> product_innname = FormFieldDTO.of("");
	private FormFieldDTO<String> dos_strength = FormFieldDTO.of("");
	private FormFieldDTO<String> dos_unit = FormFieldDTO.of("");
	
	private boolean readOnly=false;
	//show only selected rows
	private boolean selectedOnly=false;

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TableQtb getTable() {
		return table;
	}

	public void setTable(TableQtb table) {
		this.table = table;
	}

	public TableQtb getSelectedtable() {
		return selectedtable;
	}

	public void setSelectedtable(TableQtb selectedtable) {
		this.selectedtable = selectedtable;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isSelectedOnly() {
		return selectedOnly;
	}

	public void setSelectedOnly(boolean selectedOnly) {
		this.selectedOnly = selectedOnly;
	}
	
	
	public FormFieldDTO<String> getProduct_innname() {
		return product_innname;
	}

	public void setProduct_innname(FormFieldDTO<String> product_innname) {
		this.product_innname = product_innname;
	}

	public FormFieldDTO<String> getDos_strength() {
		return dos_strength;
	}

	public void setDos_strength(FormFieldDTO<String> dos_strength) {
		this.dos_strength = dos_strength;
	}

	public FormFieldDTO<String> getDos_unit() {
		return dos_unit;
	}
	public void setDos_unit(FormFieldDTO<String> dos_unit) {
		this.dos_unit = dos_unit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
