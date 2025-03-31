import { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";

import AuthService from "../services/AuthService";

type Props = {};

type State = {
    username: string,
    email: string,
    password: string,
    role: string,
    successful: boolean,
    message: string
};

export default class Register extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.handleRegister = this.handleRegister.bind(this);
        //this.handleRoleChange = this.handleRoleChange.bind(this);

        this.state = {
            username: "",
            email: "",
            role: "",
            password: "",
            successful: false,
            message: ""
        };
    }

    validationSchema() {
        return Yup.object().shape({
            username: Yup.string()
                .test(
                    "len",
                    "The username must be between 3 and 20 characters.",
                    (val: any) =>
                        val &&
                        val.toString().length >= 3 &&
                        val.toString().length <= 20
                )
                .required("This field is required!"),
            email: Yup.string()
                .email("This is not a valid email.")
                .required("This field is required!"),
            password: Yup.string()
                .test(
                    "len",
                    "The password must be between 6 and 40 characters.",
                    (val: any) =>
                        val &&
                        val.toString().length >= 6 &&
                        val.toString().length <= 40
                )
                .required("This field is required!"),
        });
    }
/*
    handleRoleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.setState({role: [event.target.value]});
    }

 */



    handleRegister(formValue: { username: string; email: string; role: string; password: string}) {
        const { username, email, role, password  } = formValue;
        const roleTransmitted: Array<string> = [];
        roleTransmitted.push(role);

        this.setState({
            message: "",
            successful: false
        });

        AuthService.register(
            username,
            email,
            roleTransmitted,
            password
        ).then(
            response => {
                this.setState({
                    message: response.data.message,//
                    successful: true
                });
            },
            error => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                this.setState({
                    successful: false,
                    message: resMessage
                });
            }
        );
    }

    render() {
        const { successful, message } = this.state;

        const initialValues = {
            username: "",
            email: "",
            role: "",
            password: ""

        };

        // @ts-ignore
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    <img
                        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                        alt="profile-img"
                        className="profile-img-card"
                    />

                    <Formik
                        initialValues={initialValues}
                        validationSchema={this.validationSchema}
                        onSubmit={this.handleRegister}
                    >
                        <Form>
                            {!successful && (
                                <div>
                                    <div className="form-group">
                                        <label htmlFor="username"> Username </label>
                                        <Field name="username" type="text" className="form-control" />
                                        <ErrorMessage
                                            name="username"
                                            component="div"
                                            className="alert alert-danger"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="email"> Email </label>
                                        <Field name="email" type="email" className="form-control" />
                                        <ErrorMessage
                                            name="email"
                                            component="div"
                                            className="alert alert-danger"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="password"> Password </label>
                                        <Field
                                            name="password"
                                            type="password"
                                            className="form-control"
                                        />
                                        <ErrorMessage
                                            name="password"
                                            component="div"
                                            className="alert alert-danger"
                                        />
                                    </div>

                                    {/* Radio buttons for role selection */}
                                    <div className="form-group">
                                        <label>Sign up with:</label>
                                        <div className="form-check">
                                            <Field
                                                type="radio"
                                                name="role"
                                                value="user"
                                                className="form-check-input"
                                            />
                                            <label htmlFor="role" className="form-check-label">User Role</label>
                                        </div>
                                        <div className="form-check">
                                            <Field
                                                type="radio"
                                                name="role"
                                                value="provider"
                                                className="form-check-input"
                                            />
                                            <label htmlFor="role" className="form-check-label">Provider Role</label>
                                        </div>
                                        <ErrorMessage
                                            name="role"
                                            component="div"
                                            className="alert alert-danger"
                                        />
                                    </div>

                                    <div className="form-group signupform">
                                        <button type="submit" className="btn btn-primary btn-block signupbutton">Sign Up</button>
                                    </div>
                                </div>
                            )}

                            {message && (
                                <div className="form-group">
                                    <div
                                        className={
                                            successful ? "alert alert-success" : "alert alert-danger"
                                        }
                                        role="alert"
                                    >
                                        {message}
                                    </div>
                                </div>
                            )}
                        </Form>
                    </Formik>
                </div>
            </div>
        );
    }
}