var breedsApi = Vue.resource('pet/{id}');

Vue.component('breeds-raw', {
  props:['breed'],
  template: '<div><i>({{ breed.id }})</i> {{ breed.title }}</div>'
});


Vue.component('breeds-list', {
  props:['breeds'],
  template: '<div><breeds-raw v-for="breed in breeds" :key="breed.id" :breed = "breed" /></div>',
  created: function() {
        breedsApi.get().then(result =>
           result.json().then(data =>
                data.forEach(breed => this.breeds.push(breed))
           )
        )
  }
});

var app = new Vue({
  el: '#app',
  template: '<breeds-list :breeds = "breeds"  />',
  data: {
    breeds: []
  }
});