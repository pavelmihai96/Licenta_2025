import axios from 'axios';
import authHeader from './AuthHeader';
import SubscriptionResponse from "../payload/response/SubscriptionResponse";
import SubReq from "../payload/request/SubReq";

const API_BASE_URL = 'http://localhost:8080/licenta/';


class SubscriptionService {
    addSubscription(data: any) {
        return axios.post(API_BASE_URL + 'subs', data, { headers: authHeader() });
    }

    getSubscriptionById(user_id: number, provider_id: number){
        return axios.get(API_BASE_URL + 'subs' + "/" + user_id + "/" + provider_id, { headers: authHeader() });
    }

    getAllSubscriptions(user_id: number) {
        return axios.get(API_BASE_URL + 'subs/' + user_id, { headers: authHeader() });
    }

    updateStatus(user_id: number, provider_id: number, data: SubReq) {
        return axios.put<string>(API_BASE_URL + 'subs/'+ user_id + "/" + provider_id, data, { headers: authHeader() });
    }
}

export default new SubscriptionService();