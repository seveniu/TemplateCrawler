# vue-multi-page

> vue multi page

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# build之后的文件预览
npm run preview

```

## css预加载配置

1、sass配置

``` bash
npm install node-sass --save-dev

npm install sass-loader --save-dev
```

2、less配置

``` bash
npm install less --save-dev

npm install less-loader --save-dev
```

然后在对应的组件或者视图的style标签加上lang

``` html
<style lang="sass" scoped>
    .....
</style>
```


For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
