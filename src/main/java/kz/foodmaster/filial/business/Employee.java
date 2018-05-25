package kz.foodmaster.filial.business;

import java.util.Date;

public class Employee {
	private int employeeId;
	private int positionId;
    private String employeeName;
    private Date employeeBirthDate;
    private String employeePhone;
    private String employeeNotes;
    
    public Employee() {
    	employeeBirthDate = null;
    	employeeName = "";
    	employeePhone = "";
    	employeeNotes = "";
    }

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getEmployeeBirthDate() {
		return employeeBirthDate;
	}

	public void setEmployeeBirthDate(Date employeeBirthDate) {
		this.employeeBirthDate = employeeBirthDate;
	}

	public String getEmployeePhone() {
		return employeePhone;
	}

	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}

	public String getEmployeeNotes() {
		return employeeNotes;
	}

	public void setEmployeeNotes(String employeeNotes) {
		this.employeeNotes = employeeNotes;
	}
    
}
