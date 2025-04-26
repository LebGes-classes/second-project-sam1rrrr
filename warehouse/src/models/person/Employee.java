package models.person;

public class Employee extends Person {
    public String position;
    public int salary;
    public int workPlaceId;
    public boolean isActive;

    public Employee() {}

    public void setPosition(String position) { this.position = position; }
    public void setSalary(int salary) { this.salary = salary; }
    public void setWorkPlaceId(int work_place_id) { this.workPlaceId = work_place_id; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    @Override
    public String toString() {
        //return "Employee [id=" + id + ", name=" + name + ", position=" + position + ", salary=" + salary + ", work_place_id=" + workPlaceId + "]";
        return "Работник №" + id + "\t|\tИмя: " + name + "\t|\tДолжность: " + position + "\t|\tЗарплата: " + salary + "\t|\tМесто работы: " + workPlaceId;
    }
}
