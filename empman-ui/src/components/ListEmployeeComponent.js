import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import EmployeeService from '../services/EmployeeService'

const ListEmployeeComponent = () => {

    const [employees, setEmployees] = useState([])
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(5);

    useEffect(() => {

        getEmployeesByPage(0, 5);
    }, [])

    const getEmployeesByPage = (pageNo, pageSize) => {
        EmployeeService.getEmployeesByPage(pageNo, pageSize).then((response) => {
            setEmployees(response.data)
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    const deleteEmployee = (employeeId) => {
        EmployeeService.deleteEmployee(employeeId).then((response) => {
            getEmployeesByPage(pageNo, pageSize);

        }).catch(error => {
            console.log(error);
        })

    }

    const doPagination = () =>{
        getEmployeesByPage(pageNo, pageSize);
    }

    return (
        <div className="container">
            <h2 className="text-center"> List Employees </h2>
            <Link to="/add-employee" className="btn btn-primary mb-2" > Add Employee </Link>
            <table className="table table-bordered table-striped">
                <thead>
                    <th> Employee Id </th>
                    <th> Employee First Name </th>
                    <th> Employee Last Name </th>
                    <th> Address </th>
                    <th> Actions </th>
                </thead>
                <tbody>
                    {
                        employees.map(
                            employee =>
                                <tr key={employee.employeeId}>
                                    <td> {employee.employeeId} </td>
                                    <td> {employee.firstName} </td>
                                    <td>{employee.lastName}</td>
                                    <td>{employee.address.map((a, i) => {
                                        return (
                                            <>
                                                {i + 1}. {a.street}, {a.city}, {a.pincode}
                                                <br />
                                            </>
                                        )
                                    })}</td>
                                    <td>
                                        <Link className="btn btn-info" to={`/edit-employee/${employee.employeeId}`} >Update</Link>
                                        <button className="btn btn-danger" onClick={() => deleteEmployee(employee.employeeId)}
                                            style={{ marginLeft: "10px" }}> Delete</button>
                                    </td>
                                </tr>
                        )
                    }
                </tbody>
            </table>
                <br />
                    Page no <input
                        type="text"
                        placeholder="PageNo."
                        name="pageNo"
                        value={pageNo}
                        onChange={(e) => setPageNo(e.target.value)}
                    >
                    </input>
                    &nbsp;&nbsp;
                    Page size <input
                        type="text"
                        placeholder="Page size"
                        name="pageSize"
                        value={pageSize}
                        onChange={(e) => setPageSize(e.target.value)}
                    >
                    </input>
                    <button className="btn btn-primary" onClick={() => doPagination()}
                                            style={{ marginLeft: "10px" }}> Click</button>
        </div>
    )
}

export default ListEmployeeComponent