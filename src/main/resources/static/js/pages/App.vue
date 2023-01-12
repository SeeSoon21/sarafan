<template>
  <div class="main-app">
    <div v-if="!profile">Необходимо авторизоваться через
      <a href="/login">Google</a>
    </div>
    <div v-else>
      <div>{{ profile.name }}<a href="/logout"></a></div>
      <messages-list :messages="messages"/>
    </div>
  </div>
</template>

<script>
import MessagesList from "components/messages/MessageList.vue";
//фигурные скобки потому, что тут мы импортируем функцию
import { addHandler } from "util/ws";
import { getIndex } from "util/collections";

export default {
  components: {
    MessagesList
  },
  data: function() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  //добавляем подписку на активности(/activity)
  created() {
    addHandler(data => {
      let index = getIndex(this.messages, data.id);
      //если >-1, то такое сообщение в списке уже есть
      if (index > -1) {
        this.messages.splice(index, 1, data)
      } else {
        this.messages.push(data)
      }
    })
  }

}
</script>

<style>
.main-app {
  color: maroon;
}
</style>