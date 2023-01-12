import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
//инициируем соединение с сервером
import { connect } from './util/ws'
import Vuetify from "vuetify";

//совершаем подключение по вебсокету
if(frontendData.profile) {
    connect()
}

Vue.use(VueResource)
Vue.use(Vuetify)

new Vue({
    el: "#app",
    render: a => a(App),
    vuetify: new Vuetify({})
})

