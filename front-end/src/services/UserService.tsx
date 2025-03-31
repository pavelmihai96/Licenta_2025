import axios from 'axios';
import authHeader from './AuthHeader';

const API_BASE_URL = 'http://localhost:8080/licenta/';

class UserService {
    getPublicContent() {
        return axios.get(API_BASE_URL + 'test/all');
    }

    getUser() {
        return axios.get(API_BASE_URL + 'test/user', { headers: authHeader() });
    }

    getProvider() {
        return axios.get(API_BASE_URL + 'test/prov', { headers: authHeader() });
    }

    getAllProviders(){
        console.log(authHeader());
        return axios.get(API_BASE_URL + 'provs', { headers: authHeader() });
    }

}

export default new UserService();