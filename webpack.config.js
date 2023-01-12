//получаем утилиту path для построения
//у разные ОС -- разные разделители. Поэтому лучше разделять вот так
const path = require('path');
//этот плагин используется в разделе плагинов
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    //develompent -- это пока не пришло время деплоя
    mode: 'development',
    devtool: 'source-map',
    //entry-point -- те файлы, с которых собираем исходный код(src/main.resources/.../main.js)
    //т.е. с main.js webpack будет начинать сборку проекта
    entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),
    devServer: {
        //dist -- директория, в которую webpack будет складывать файлы
        contentBase: './dist',
        compress: true,
        //на этом порту работает сам сервер
        port: 8000,
        //принимает ресурсы с этого хоста
        allowedHosts: [
            'localhost:8080'
        ]
    },
    module: {
        rules: [
            //чз @bable пропускаем все js-файлы
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            //все vue-файлы пропускаем ч/з vue-loader
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    //чтобы webpack мог резолвить импорты в нашем исходном коде
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}