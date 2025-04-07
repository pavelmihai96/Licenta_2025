import {Component} from "react";
import UserService from "../services/UserService";
import eventFile from "../eventFile";
import AuthService from "../services/AuthService";
import SubscriptionService from "../services/SubscriptionService";
import SubscriptionResponse from "../payload/response/SubscriptionResponse";
//import SubscriptionRequest from "../payload/request/SubscriptionRequest";
import SubReq from "../payload/request/SubReq";

type Props = {};

type State = {
    content: string,
    subscriptions: Array<SubscriptionResponse>;
}

export default class SubscriptionComponent extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            content: "",
            subscriptions: []
        };
    }

    handleClick(provider_id: number, status: string) {

        SubscriptionService.updateStatus(AuthService.getCurrentUser().id, provider_id, {status: status}).then(
            response => {
                this.setState({
                    content: response.data
                });
                console.log("stare schimbata");

                SubscriptionService.getAllSubscriptions(AuthService.getCurrentUser().id).then(
                    response => {
                        this.setState({
                            subscriptions: response.data
                        });
                        console.log("in handle click: " + response.data);
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
        SubscriptionService.getAllSubscriptions(AuthService.getCurrentUser().id).then(
            response => {
                this.setState({
                    subscriptions: response.data
                });
                console.log("in componentdidmount: " + response.data);
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

                    <ul>
                        {this.state.subscriptions &&
                            this.state.subscriptions.map((s, index) =>
                                <li key={index} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", gap: "1px" }}>
                                    <span>Id: {s.provider_id},  Name: {s.username},  status: {s.status},  last index: {s.index_old}</span>
                                    { s.status === "active" ? (
                                        <button onClick={() => this.handleClick(s.provider_id, "inactive")}>Unsubscribe</button>
                                    ) : (
                                        <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
                                            <span style={{ color: "red" }}>Inactive</span>
                                            <button onClick={() => this.handleClick(s.provider_id, "active")}>Subscribe</button>
                                        </div>
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