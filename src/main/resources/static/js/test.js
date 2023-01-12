let flowerApi = Vue.resource("/flower{/id}");

Vue.component('flower-row', {
    props: ['flower', 'editMethod'],
    template:
        '<div> ' +
        '<i> {{ flower.id }} </i>: {{ flower.text }} ' +
        '<span>' +
        '<input type="button" value="Edit" @click="edit" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function() {
            //передаем реализованной в flowers-list функции аргумент
            this.editMethod(this.flower);
        }
    }
});

Vue.component('flower-form', {
    props: ['flowers', 'flowerAttr'],
    //return -- просто потому, что принято такое соглашение.
    //на самом деле можно видеть это без return -- просто, как объект данных
    data: function() {
        return {
            //тут мы просто иницилизируем поля, чтобы указать их как v-model в template
            //это просто стартовые значения, которые могу быть видны пользователю
            //например, text -- это просто то, что находится в инпуте
            text: '',
            id: ''
        }
    },
    //работает только при изменении. при сохранении не срабатывает
    watch: {
        flowerAttr: function(newVal, oldVal) {
            console.log("новое значение(сработал watch): " + newVal + ", старое значение: " + oldVal);
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        ' <input type="text" placeholder="flower" v-model="text" /> ' +
        ' <input type="button" value="Save" @click="save" /> ' +
        '</div>',
    methods: {
        save: function() {
            //собираем данные с поля ввода
            let flower = {text: this.text};
            console.log("flower(this.text): " + flower.text);

            //this.id == null, if запись новая и только создается(как и text)
            if (this.id) {
                flowerApi.update({id: this.id}, flower).then(result =>
                    result.json().then( data => {
                        console.log("data(update):: " + data.id + ":" + data.text);

                        let index = getIndex(this.flowers, this.id);

                        this.flowers.splice(index, 1, data);
                    })
                )
            }
            else {
                flowerApi.save({}, flower).then(result =>
                    result.json().then(data =>
                    {
                        this.flowers.push(data);
                        console.log("this.text до обнуления: " + this.text);
                        this.text = '';
                        console.log("this.text после: " + this.text);

                    })
                )
            }

        }
    }

});

Vue.component('flowers-list', {
    props: ['flowers'],
    data: function () {
        return {
            flower: null
        }
    },
    //для каждого flower в flowers мы создаем свой div
    template:
        '<div>' +
        '<flower-row v-for="flower in flowers" :key="flower.id" :flower="flower" ' +
        ':editMethod="editMethod" :flowers="flowers"/> ' +
        '<flower-form :flowers="flowers" :flowerAttr="flower" /> ' +
        '</div>',
    created: function () {
        flowerApi.get().then(result =>
            result.json().then(data =>
                data.forEach(flower =>
                    this.flowers.push(flower)
                )
            )
        )
    },
    methods: {
        //flower из этой функции мы передадим в другую -- flower-row
        editMethod: function (flower) {
            this.flowers = flower;
        }
    }
});

let vm = new Vue({
    el: '#flow',
    template: '<flowers-list :flowers="flowers" />',
    data: {
        flowers: []
    }
});