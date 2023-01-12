<template>
  <div>
    <input type="text" placeholder="Write smth" v-model="text"/>
    <input type="button" value="save" @click="save"/>
  </div>
</template>

<script>
import { sendMessage } from "util/ws";

export default {
  props: ['messages', 'messageAttr'],
  data() {
    return {
      text: '',
      id: ''
    }
  },
  //Всякий раз, когда будет меняться messageAttr
  watch: {
    messageAttr: function (newVal, oldVal) {
      this.text = newVal.text;
      this.id = newVal.id;
    }
  },
  //v-model -- связь переменной text, значение в которой будем сохранять и html-инпута
  //@click == v-on -- аналогия onclick
  methods: {
    save() {
      //если id нет -- уйдёт с id=null(т.е. новое)
      sendMessage({id: this.id, text: this.text})
      this.text = ''
      this.id = ''

    }
  }
}
</script>

<style>

</style>