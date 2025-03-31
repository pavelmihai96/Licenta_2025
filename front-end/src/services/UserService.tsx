import axios from 'axios';
import authHeader from './AuthHeader';

const API_BASE_URL = 'http://localhost:8080/licenta/test/';

class UserService {
    getPublicContent() {
        return axios.get(API_BASE_URL + 'all');
    }

    getUser() {
        return axios.get(API_BASE_URL + 'user', { headers: authHeader() });
    }

    getProvider() {
        return axios.get(API_BASE_URL + 'prov', { headers: authHeader() });
    }

}

export default new UserService();