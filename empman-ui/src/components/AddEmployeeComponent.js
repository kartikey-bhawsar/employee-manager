import React, {useState, useEffect} from 'react'
import {Link, useNavigate, useParams } from 'react-router-dom';
import EmployeeService from '../services/EmployeeService'

const AddEmployeeComponent = () => {

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [address, setAddress] = useState([
        {street: "", city: "", pincode: null}
    ])
    const [error,setError]=useState("")
    // const [street, setStreet] = useState('')
    // const [city, setCity] = useState('')
    // const [pincode, setPincode] = useState()
    const history = useNavigate();
    const {employeeId} = useParams();

    const saveOrUpdateEmployee = (e) => {
        e.preventDefault();
        

        const employee = {firstName, lastName, address}
        console.log(employee);

        if(employeeId){
            EmployeeService.updateEmployee(employeeId, employee).then((response) => {
                history('/employees')
            }).catch(error => {
                setError(error.response.data.errorMessage)
                console.log(error)
            })

        }else{
            EmployeeService.createEmployee(employee).then((response) =>{

                console.log(response.data)
    
                history('/employees');
    
            }).catch(error => {
                setError(error.response.data.errorMessage)
                console.log(error.response.data.errorMessage)
            })
        }
        
    }

    const handleInputChange = (e, index) => {
        const { name, value } = e.target;
        const list = [...address];
        list[index][name] = value;
        setAddress(list);
      };

      const handleRemoveClick = index => {
        const list = [...address];
        list.splice(index, 1);
        setAddress(list);
      };
     
      // handle click event of the Add button
      const handleAddClick = () => {
        setAddress([...address, { street: "", city: "", pincode: null }]);
      };

    useEffect(() => {

        EmployeeService.getEmployeeById(employeeId).then((response) =>{
            setFirstName(response.data.firstName)
            setLastName(response.data.lastName)
            setAddress(response.data.address)
        }).catch(error => {
            console.log(error)
        })
    }, [] )

    const title = () => {

        if(employeeId){
            return <h2 className = "text-center">Update Employee</h2>
        }else{
            return <h2 className = "text-center">Add Employee</h2>
        }
    }

    return (
        <div>
           <br /><br />
           <div className = "container">
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                       {
                           title()
                       }
                        <div className = "card-body">
                            <form>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> First Name :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter first name"
                                        name = "firstName"
                                        className = "form-control"
                                        value = {firstName}
                                        onChange = {(e) => setFirstName(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Last Name :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter last name"
                                        name = "lastName"
                                        className = "form-control"
                                        value = {lastName}
                                        onChange = {(e) => setLastName(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Address :</label>
                                {   address.map((x,i)=>{
                                    return(
                                    <>
                                    <div className = "form-group mb-2">
                                    <label className = "form-label"> Street :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter street"
                                        name = "street"
                                        className = "form-control"
                                        value = {x.street}
                                        onChange = {(e) => handleInputChange(e,i)}
                                    >
                                    </input>
                                    </div>
                                    <div className = "form-group mb-2">
                                    <label className = "form-label"> City :</label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter City"
                                        name = "city"
                                        className = "form-control"
                                        value = {x.city}
                                        onChange = {(e) => handleInputChange(e,i)}
                                    >
                                    </input>
                                    </div>
                                    <div className = "form-group mb-2">
                                    <label className = "form-label"> Pincode :</label>
                                    <input
                                        type = "number"
                                        placeholder = "Enter pincode"
                                        name = "pincode"
                                        className = "form-control"
                                        value = {x.pincode}
                                        onChange = {(e) => handleInputChange(e,i)}
                                    >
                                    </input>
                                    <div className="btn-box">
                                        {address.length !== 1 && <button className="mr10" onClick={() => handleRemoveClick(i)}>Remove</button>}
                                        {address.length - 1 === i && <button onClick={handleAddClick}>Add</button>}
                                     </div>
                                    </div>
                                    </>
                                    )
                                }) 
                                }
                                </div>
                                <div style={{color: "red"}}>{error}</div> <br />
                                <button className = "btn btn-success" onClick = {(e) => saveOrUpdateEmployee(e)} >Submit </button>
                                <Link to="/employees" className="btn btn-danger"> Cancel </Link>
                            </form>

                        </div>
                    </div>
                </div>

           </div>

        </div>
    )
}

export default AddEmployeeComponent