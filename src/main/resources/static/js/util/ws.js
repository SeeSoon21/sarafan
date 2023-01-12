import SockJS from 'sockjs-client'
//стомп нужно импортировать через деструктруризацию
import { Stomp } from '@stomp/stompjs'

let stompClient = null
//список будет каждый раз пробегаться, когда будет срабатывать подписка /topic/activity
const handlers = []

export function connect() {
    console.log("Должно быть сообщение о коннекшене");
    const socket = new SockJS('/gs-guide-websocket')
    stompClient = Stomp.over(socket)
    //отключаем логирование
    stompClient.debug = () => {}
    stompClient.connect({}, frame => {
        console.log('connected: ' + frame)
        //прописываем обработку сообщений от сервера
        stompClient.subscribe('/topic/activity', message => {
            console.log('message(topic/activity): ' + message)
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler);
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("disconnected")
}

export function sendMessage(message) {
    //если сообщение существует -- id высветится
    console.log("(отправка по сокету) " + message.id + ":" + message.text);
    stompClient.send('/app/changeMessage', {}, JSON.stringify(message))
}
