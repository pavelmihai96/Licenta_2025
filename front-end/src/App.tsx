import { Component } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/AuthService";
import UserType from './payload/response/UserType';

import Login from "./components/LoginComponent";
import Register from "./components/RegisterComponent";
import Home from "./components/HomeComponent";
import Profile from "./components/ProfileComponent";
import BoardUser from "./components/BoardUserComponent";
import BoardProvider from "./components/BoardProviderComponent";
import SubscriptionComponent from "./components/SubscriptionComponent";
import InvoiceComponent from "./components/InvoiceComponent";

import eventFile from "./eventFile";

type Props = {};

type State = {
  showProvider: boolean,
  showUser: boolean,
  currentUser: UserType | undefined
}

class App extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showProvider: false,
      showUser: false,
      currentUser: undefined
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        showProvider: user.roles.includes("ROLE_PROVIDER"),
        showUser: user.roles.includes("ROLE_USER"),
        currentUser: user
      });
    }

    eventFile.on("logout", this.logOut);
  }

  componentWillUnmount() {
    eventFile.remove("logout", this.logOut);
  }

  logOut() {
    AuthService.logout();
    this.setState({
      showProvider: false,
      showUser: false,
      currentUser: undefined
    });
  }

  render() {
    const { showProvider, showUser, currentUser } = this.state;

    return (
        <div>
          <nav className="navbar navbar-expand navbar-light bg-primary">
            <Link to={"/"} className="navbar-brand appname">
              InPay
            </Link>
            <div className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to={"/home"} className="nav-link">
                  Home
                </Link>
              </li>

              {showProvider && (
                  <li className="nav-item">
                    <Link to={"/prov"} className="nav-link">
                      General
                    </Link>
                  </li>
              )}

              {showUser && (
                  <li className="nav-item">
                    <Link to={"/user"} className="nav-link">
                      Providers
                    </Link>
                  </li>
              )}
              {showUser && (
                  <li className="nav-item">
                    <Link to={"/user/sub"} className="nav-link">
                      Subscriptions
                    </Link>
                  </li>
              )}
              {showUser && (
                  <li className="nav-item">
                    <Link to={"/user/inv"} className="nav-link">
                      Invoices
                    </Link>
                  </li>
              )}
            </div>

            {currentUser ? (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/profile"} className="nav-link">
                      {currentUser.username}
                    </Link>
                  </li>
                  <li className="nav-item">
                    <a href="/login" className="nav-link" onClick={this.logOut}>
                      LogOut
                    </a>
                  </li>
                </div>
            ) : (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">
                      Login
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/register"} className="nav-link">
                      Sign Up
                    </Link>
                  </li>
                </div>
            )}
          </nav>

          <div className="container mt-3">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/home" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/user" element={<BoardUser />} />
              <Route path="/user/sub" element={<SubscriptionComponent />} />
              <Route path="/user/inv" element={<InvoiceComponent />} />
              <Route path="/prov" element={<BoardProvider />} />
            </Routes>
          </div>

          { /*<AuthVerify logOut={this.logOut}/> */}
        </div>
    );
  }
}

export default App;