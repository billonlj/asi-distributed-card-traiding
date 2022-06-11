import userService from "./userService.js";
import API from "../utils/axios.js";

export default {
    async getCardUser(idUser) {
        if (idUser === undefined) {
            idUser = userService.get_user_id();
        }

        try {
            const response = await API.get('/cards/users/' + idUser);
            return response.data;
        } catch (error) {
            return [];  
        }
    },
    async getCardById(idCard) {
        const response = await API.get('/card/' + idCard)
        return response.data;
    }
}