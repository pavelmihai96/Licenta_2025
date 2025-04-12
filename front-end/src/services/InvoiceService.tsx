import axios from 'axios';
import authHeader from './AuthHeader';
import SubscriptionResponse from "../payload/response/SubscriptionResponse";
import SubReq from "../payload/request/SubReq";

const API_BASE_URL = 'http://localhost:8080/licenta/';

class InvoiceService {
    getAllInvoices(user_id: number) {
        return axios.get(API_BASE_URL + 'inv/' + user_id, { headers: authHeader() });
    }

    pay() {
        return axios.put<string>(API_BASE_URL + 'inv', { headers: authHeader() });
    }
}

export default new InvoiceService();