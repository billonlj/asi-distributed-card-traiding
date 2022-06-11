import Store from "../utils/store.js";
import API from "../utils/axios.js";
import userService from "../services/userService.js"

const baseSSEUrl = "http://localhost/api/rooms/sse";
const getHeaders = () => ({
    headers: {
        Authorization: Store.getItem('user')?.token
    }
})

const availableRoomsEvents = {}
const streamAvailableRooms = new EventSourcePolyfill(baseSSEUrl, getHeaders());
streamAvailableRooms.onmessage = (event) => {
    const rooms = JSON.parse(event.data);

    for (const [key, availableRoomsEvent] of Object.entries(availableRoomsEvents)) {
        availableRoomsEvent(rooms);
    }
}

const currentRoomEvents = {}
let currentRoom = null;
const setCurrentRoom = (eventSource) => {
    if (currentRoom) currentRoom.close();

    currentRoom = eventSource;
    currentRoom.onmessage = (event) => {
        try {
            const data = JSON.parse(event.data);
            console.log(data)
    
            for (const [key, currentRoomEvent] of Object.entries(currentRoomEvents)) {
                currentRoomEvent(data);
            }
            
        } catch (error) {
            console.log("Error: ", event)
        }
    }
}

const isConnectedToRoom = () => {
    return currentRoom !== undefined;
}

const joinRoomSSE = (room) => {
    setCurrentRoom(new EventSourcePolyfill(baseSSEUrl + "/join/" + room.idRoom + "/" + userService.get_user_id(), getHeaders()));
}

export default {
    async createRoom(data) {
        try {
            const response = await API.post('/rooms', data);
            return response.data;
        } catch (error) {   
            return null;
        }
    },
    async joinRoom(roomDto) {
        try {
            const response = await API.post('/rooms/join', {
                ...roomDto,
                idUser: userService.get_user_id()
            });
            joinRoomSSE(roomDto);
            return response.data;
        } catch (error) {   
            return null;
        }
    },

    async getAvailabeRooms() {
        try {
            const response = await API.get('/rooms');
            return response.data
        } catch (error) {
            return [];            
        }
    },


    isConnectedToRoom,
    addCurrentRoomEventListener(key, callback) {
        currentRoomEvents[key] = callback;

    },
    removeCurrentRoomEventListener(key) {
        delete currentRoomEvents[key];
    },
    addAvailableRoomsEventListener(key, callback) {
        availableRoomsEvents[key] = callback;

    },
    removeAvailableRoomsEventListener(key) {
        delete availableRoomsEvents[key];
    },
}