import { Component } from "react";

import UserService from "../services/UserService";
import eventFile from "../eventFile";
import UserType from "../payload/response/UserType";
import UserResponse from "../payload/response/UserResponse";
import SubscriptionService from "../services/SubscriptionService";
import SubscriptionRequest from "../payload/request/SubscriptionRequest";
import AuthService from "../services/AuthService";

type Props = {};

type State = {
    content: string;
    providers: Array<UserResponse>,
    message: boolean
}

export default class ProvidersPage extends Component<Props, State> {
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

                UserService.getAllProviders(AuthService.getCurrentUser().id).then(
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
        UserService.getAllProviders(AuthService.getCurrentUser().id).then(
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
                    <h1>Below are all the providers:</h1>

                    <ul>
                        {this.state.providers &&
                            this.state.providers.map((p, index) =>
                                <li key={index} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", gap: "1px" }}>
                                    <span>Id: {p.id},  Name: {p.username}, Email: {p.email}</span>
                                    { p.subscribed === "Not Subscribed" ? (
                                        <button onClick={() => this.handleClick(p.id)}>Subscribe</button>
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