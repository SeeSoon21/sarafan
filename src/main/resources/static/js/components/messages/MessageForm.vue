<template>
  <div>
    <input type="text" placeholder="Write smth" v-model="text"/>
    <input type="button" value="save" @click="save"/>
  </div>
</template>

<script>
function getIndex(list, id) {
  //i = id - 1(если без пропусков, типа 17, 25)
  for (let i = 0; i < list.length; i++) {
    if (list[i].id === id) {
      return i;
    }
  }

  return -1;
}

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
      const message = {text: this.text};

      //добавление нового объекта или обновление старого
      if (this.id) {
        this.$resource('/message{/id}').update({id: this.id}, message).then(result =>
            result.json().then(data => {
              //сообщение с таким id в коллекции уже есть
              //так что нужно его сначала удалить
              const index = getIndex(this.messages, data.id);

              //splice -- замена элемента по index'у
              //index -- индекс заменяемого элемента
              //1 -- число заменяемых элементов
              //data -- элемент, на который мы хотим заменить
              this.messages.splice(index, 1, data);
            })
        )
      } else {
        //сохраняем сообщение(save ожидает на вход айдишник и объект)
        //{} -- id-ник
        //вот тут ожидается ответ от сервера(установка айди и тд)
        this.$resource('/message{/id}').save({}, message).then(result =>
            result.json().then(data => {
              //добавляем в массив сообщений message
              this.messages.push(data);
              this.text = '';
            })
        );
      }
    }
  }
}
</script>

<style>

</style>