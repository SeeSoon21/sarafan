<template>
  <div>
    <i>({{ message.id }})</i> {{ message.text }}
    <span
        style="position: absolute; right: 0">
        <input type="button" value="Edit" @click="edit"/>
        <input type="button" value="X" @click="del"/>
    </span>
  </div>
</template>

<script>
export default {
  props: ['message', 'editMessage', 'deleteMessage', 'messages'],
  methods: {
    //нам надо передать message в форму редактирования. для этого нам нужно перебросить prop message обратно в message-list,
    // который будет передавать его уже в форму. для этого создаём методы в message-list
    edit: function () {
      this.editMessage(this.message);
    },
    del: function () {
      this.deleteMessage(this.message);
    }
  }
}
</script>

<style>

</style>