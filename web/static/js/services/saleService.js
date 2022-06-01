import userService from "./userService.js"
import API from "../utils/axios.js";

export default {
    async buyCard(transaction) {
        try {
            const idUser = userService.get_user_id();
            const response = await API.post('/sales/buy', {...transaction, idUser});
            userService.refreshUserProfile();
            return response.data;
        } catch (error) {
            return false;
        }
    },
    async sellCard(transaction) {
        try {
            const idUser = userService.get_user_id();
            const response = await API.post('/sales/sell', {...transaction, idUser});
            userService.refreshUserProfile();
            return response.data;
        } catch (error) {
            return false;
        }
    },
    async getSellableCards() {
        try {
            const response = await API.get('/sales/');
            return response.data;
        } catch (error) {
            return [];  
        }
    }
}