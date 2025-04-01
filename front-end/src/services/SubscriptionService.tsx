import axios from 'axios';
import authHeader from './AuthHeader';

const API_BASE_URL = 'http://localhost:8080/licenta/';

class SubscriptionService {
    addSubscription(data: any) {
        return axios.post(API_BASE_URL + 'subs', data, { headers: authHeader() });
    }
}

export default new SubscriptionService();