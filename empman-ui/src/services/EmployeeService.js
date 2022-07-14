import axios from 'axios'

const EMPLOYEE_BASE_REST_API_URL = 'http://localhost:8080/employee';

class EmployeeService{

    getAllEmployees(){
        return axios.get(EMPLOYEE_BASE_REST_API_URL+"/getAllEmployees")
    }

    getEmployeesByPage(pageNo, pageSize){
        return axios.get(EMPLOYEE_BASE_REST_API_URL+"/pagination/"+ pageNo + "/" + pageSize)
    }

    createEmployee(employee){
        return axios.post(EMPLOYEE_BASE_REST_API_URL+"/addemployee", employee)
    }

    getEmployeeById(employeeId){
        return axios.get(EMPLOYEE_BASE_REST_API_URL + '/getEmployee/' + employeeId);
    }

    updateEmployee(employeeId, employee){
        return axios.put(EMPLOYEE_BASE_REST_API_URL + '/update/' +employeeId, employee);
    }

    deleteEmployee(employeeId){
        return axios.delete(EMPLOYEE_BASE_REST_API_URL + '/deleteEmployee/' + employeeId);
    }
}

export default new EmployeeService();