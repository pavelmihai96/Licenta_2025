import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/licenta/auth/";
//const username = "pavelnico";
//const password = "pavelnico";

class AuthService {
    login(username: string, password: string) {
        return axios
            .post(API_BASE_URL + "login", {
                username,
                password
            })
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username: string, email: string, role: Array<string>, password: string ) {
        console.log(username, email, role);
        return axios.post(API_BASE_URL + "signup", {username, email, role, password});
    }

    getCurrentUser() {
        const currentUser = localStorage.getItem("user");
        if (currentUser) return JSON.parse(currentUser);

        return null;
    }
}

export default new AuthService();