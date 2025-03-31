import { Component } from "react";

import UserService from "../services/UserService";
import eventFile from "../eventFile";
import UserType from "../UserType";

type Props = {};

type State = {
    content: string;
    providers: any
}

export default class BoardUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            content: "",
            providers: null
        };
    }

    componentDidMount() {
        UserService.getUser().then(
            response => {
                this.setState({
                    content: response.data
                });
            },
            error => {
                this.setState({
                    content:
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString()
                });

                if (error.response && error.response.status === 401) {
                    eventFile.dispatch("logout");
                }
            }
        );
        UserService.getAllProviders().then(
            response => {
                console.log(response.data);
                this.setState({
                    providers: response.data
                });
            },
            error => {
                this.setState({
                    content:
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString()
                });

                if (error.response && error.response.status === 401) {
                    eventFile.dispatch("logout");
                }
            }
        )
    }

    render() {
        return (
            <div className="container">
                <header className="jumbotron">
                    <h3>{this.state.content}</h3>

                    <h4>{this.state.providers}</h4>
                </header>
            </div>
        );
    }
}