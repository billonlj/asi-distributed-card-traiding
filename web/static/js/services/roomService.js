import Store from "../utils/store.js";
import API from "../utils/axios.js";

const baseUrl = "http://localhost:8080/api/rooms/sse";

const getHeaders = () => ({
    headers: {
        Authorization: Store.getItem('user')?.token
    }
})

const availableRoomsEvents = {}
const streamAvailableRooms = new EventSourcePolyfill(baseUrl, getHeaders());
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
        const data = JSON.parse(event.data);

        for (const [key, currentRoomEvent] of Object.entries(currentRoomEvents)) {
            currentRoomEvent(data);
        }
    }
}

const isConnectedToRoom = () => {
    return currentRoom !== undefined;
}

const joinRoom = (room) => {
    setCurrentRoom(new EventSourcePolyfill(baseUrl + "/join/" + room.idRoom, getHeaders()));
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
    joinRoom,

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