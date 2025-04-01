import { Component } from "react";

import UserService from "../services/UserService";
import eventFile from "../eventFile";
import UserType from "../UserType";
import UserResponse from "../UserResponse";
import SubscriptionService from "../services/SubscriptionService";
import SubscriptionRequest from "../SubscriptionRequest";
import AuthService from "../services/AuthService";

type Props = {};

type State = {
    content: string;
    providers: Array<UserResponse>,
    message: boolean
}

export default class BoardUser extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            content: "",
            providers: [],
            message: false
        };
    }

    handleClick(id: number) {
        const sub_data: SubscriptionRequest = {id:{user_id:0,provider_id:0}};
        sub_data.id.user_id = AuthService.getCurrentUser().id;
        sub_data.id.provider_id = id;
        sub_data.status = "active";
        sub_data.index_old = 0;
        sub_data.index_read = 0;

        SubscriptionService.addSubscription(sub_data).then(
            response => {
                console.log(response.data);
                console.log(this.state.message);
                this.setState({
                    message: true
                });
        })
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

                    <ul>
                        {this.state.providers &&
                            this.state.providers.map((prov, index) =>
                                <li key={index} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", gap: "1px" }}>
                                    <span>Id: {prov.id},  Name: {prov.username}, Email: {prov.email}</span>
                                    { !this.state.message ? (
                                        <button onClick={() => this.handleClick(prov.id)}>Subscribe</button>
                                    ) : (
                                        <span style={{ color: "green" }}>Already subscribed</span>
                                    )
                                    }
                                </li>
                            )
                        }
                    </ul>
                </header>
            </div>
        );
    }
}