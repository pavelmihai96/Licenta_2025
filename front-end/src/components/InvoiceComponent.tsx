import SubscriptionResponse from "../payload/response/SubscriptionResponse";
import {Component} from "react";
import SubscriptionService from "../services/SubscriptionService";
import AuthService from "../services/AuthService";
import eventFile from "../eventFile";
import InvoiceResponse from "../payload/response/InvoiceResponse";
import InvoiceService from "../services/InvoiceService";

type Props = {};

type State = {
    content: string,
    invoices: Array<InvoiceResponse>;
}

export default class SubscriptionComponent extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            content: "",
            invoices: []
        };
    }

    handleClick(provider_id: number, amount: number) {

        InvoiceService.pay().then(
            response => {
                this.setState({
                    content: response.data
                });
                console.log("stare schimbata");

                InvoiceService.getAllInvoices(AuthService.getCurrentUser().id).then(
                    response => {
                        this.setState({
                            invoices: response.data
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
            })

    }

    componentDidMount() {
        InvoiceService.getAllInvoices(AuthService.getCurrentUser().id).then(
            response => {
                this.setState({
                    invoices: response.data
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
                        {this.state.invoices &&
                            this.state.invoices.map((i, index) =>
                                <li key={index} style={{ display: "flex", justifyContent: "space-between", alignItems: "center", gap: "1px" }}>
                                    <span>Id: {i.provider_id},  Name: {i.username},  status: {i.status},  amount: {i.amount}$,  due date: {i.due_date?.substring(0,10)}</span>
                                    { i.status === "unpaid" ? (
                                        <button onClick={() => this.handleClick(i.provider_id, i.amount)}>Pay</button>
                                    ) : (
                                        <span style={{ color: "green" }}>Already paid</span>
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