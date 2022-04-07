import React from "react";
import loginImg from "../../login.svg";
import axios from "axios";

export class Login extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      email : '',
      password: '',
      user : !!localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  }

  render() {
    return (
      <div className="base-container" ref={this.props.containerRef}>
        <div className="header">Login</div>
        <div className="content">
          <div className="image">
            <img src={loginImg} />
          </div>
          <div className="form">
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input 
                type="text" 
                required 
                name="email" 
                placeholder="email"
                value={this.state.email} 
                onChange={e => this.setState({[e.target.name] : e.target.value})}>
              </input>
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input 
                type="password" 
                required 
                name="password" 
                placeholder="password" 
                value={this.state.password} 
                onChange={e => this.setState({[e.target.name] : e.target.value})} >
              </input>
            </div>
          </div>
        </div>
        <div className="footer">
          <button 
            type="button" 
            className="btn" 
            disabled={!this.validateForm}
            onClick = {this.handleSubmit}>
            Login
          </button>
        </div>
      </div>
    );
  }
  
  validateForm = () => {
    return this.state.email.length > 0 && this.state.password.length > 0;
  }

  handleSubmit = () => {
      axios
           .post('http://localhost:8080/auth/login', {
             email : this.state.email,
             password : this.state.password
           },
           (error, {email, password}) =>
            console.log(email," ",password)) 
           .then(res => {
             localStorage.setItem("user", JSON.stringify(res.data));
             this.props.history.push({
                pathname: "/profile"
             });
           })
           .catch(res => alert("Bad Request!"));
  }
}
