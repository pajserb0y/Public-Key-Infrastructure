import React from "react";
import loginImg from "../../login.svg";
import axios from "axios";

export class Register extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      user : {
        firstname: '',
        lastname: '',
        email: '',
        password: ''
      },
      errors: {
        user: {
          'email': 'Enter email',
          'password': 'Enter password',
          'firstName': 'Enter First name',
          'lastName': 'Enter Last name'
        }
      },
      validForm: false,
      submitted: false,
      successfullyReg:false,
      disabled: false,
      errorMessage:false
    }
  }

  render() {
    return (
      <div className="base-container" ref={this.props.containerRef}>
        <div className="header">Register</div>
        <div className="content">
          <div className="image">
            <img src={loginImg} />
          </div>
          <div className="form">
            <div className="form-group">
              <label htmlFor="Name">Name</label>
              <input 
                type="text" 
                name="firstname" 
                placeholder="First Name" 
                required 
                value={this.state.user.firstname} 
                onChange={e => this.handleInputChange(e)}>
              </input>
              {this.state.submitted && this.state.errors.user.firstName.length > 0 &&
                        <span className="text-danger">{this.state.errors.user.firstName}</span>}
            </div>
            <div className="form-group">
              <label htmlFor="Lastname">Lastname</label>
              <input 
              type="text" 
              name="lastname" 
              placeholder="Lastname" 
              required 
              value={this.state.user.lastname} 
              onChange={e => this.handleInputChange(e)}/>
              {this.state.submitted && this.state.errors.user.lastName.length > 0 && <span className="text-danger">{this.state.errors.user.lastName}</span>}
            </div>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input 
              type="text" 
              name="email" 
              placeholder="email" required 
              value={this.state.user.email} 
              onChange={e => this.handleInputChange(e)}/>
              {this.state.submitted && this.state.errors.user.email.length > 0 && <span className="text-danger">{this.state.errors.user.email}</span>}
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input 
              type="password" 
              name="password" 
              placeholder="password" required 
              value={this.state.user.password} 
              onChange={e => this.handleInputChange(e)}/>
              {this.state.submitted && this.state.errors.user.password.length > 0 &&  <span className="text-danger">{this.state.errors.user.password}</span>}
            </div>
          </div>
        </div>
        <div className="footer">
          <button type="button" className="btn" onClick={this.handleSubmit}>
            Register
          </button>
        </div>
      </div>
    );
  }

  handleInputChange = (event) => {
    const {name, value} = event.target;
    const user = this.state.user;
    user[name] = value;

    this.setState({ user })

    this.validationErrorMessage(event);
  }

  validationErrorMessage = (event) => {
    const {name, value} = event.target;
    let errors = this.state.errors;

    switch (name) {
        case 'firstname':
          errors.user.firstName = value.length < 1 ? 'Enter First Name' : '';
          break;
        case 'lastname': 
          errors.user.lastName = value.length < 1 ? 'Enter Last Name' : '';
          break;
        case 'email':
            errors.user.email = this.isValidEmail(value) ? '' : 'Email is not valid!';
            break;
        case 'password':
            errors.user.password = value.length < 1 ? 'Enter Password' : '';
            break;
        default:
          break;
    }

    this.setState({errors})
  }

  validateForm = (errors) => {
    let valid = true;
    Object.entries(errors.user).forEach(item => {
        item && item[1].length > 0 && (valid = false)
    })
    return valid;
}

  handleSubmit = async (event) => {
    this.setState({submitted: true});
    const user = this.state.user;
    event.preventDefault();
    if (this.validateForm(this.state.errors)) {
        console.info('Valid Form')
        console.log(this.state.user)
        this.sendParams()
    } else {
        console.log('Invalid Form')
    }
  }

  async sendParams() {
    axios
        .post('http://localhost:8080/auth/save', {
            'id':'',
            'firstName' : this.state.user.firstName,
            'lastName' : this.state.user.lastName,
            'email' : this.state.user.email,
            'password' : this.state.user.password,
        })
        .then(res => {
            this.setState({ errorMessage:false });
            this.setState({ successfullyReg:true });
            this.setState( {disabled: !this.state.disabled} )
        }).catch(res => {
        this.setState({ errorMessage:true });
        alert("Bad Request!");
    })

    ;
}

  isValidEmail = (value) => {
      return !(value && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,64}$/i.test(value))
  }

}
