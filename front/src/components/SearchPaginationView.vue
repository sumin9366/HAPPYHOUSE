<template>
  <!-- a tag href="#" 제외!!
route url 이 board 에서 / 로 변경되는 문제 발생 -->
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <div class="page-link" aria-label="Previous" @click="paginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">«</span>
        </div>
      </li>
      <li
        v-for="index in endPageIndex - startPageIndex + 1"
        :key="index"
        v-bind:class="{
          active: startPageIndex + index - 1 == $store.state.searchStore.currentPageIndex,
        }"
        class="page-item"
      >
        <div @click="paginationChanged(startPageIndex + index - 1)" class="page-link">
          {{ startPageIndex + index - 1 }}
        </div>
        <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <div class="page-link" aria-label="Next" @click="paginationChanged(endPageIndex + 1)">
          <span aria-hidden="true">»</span>
        </div>
      </li>
    </ul>
  </nav>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "SearchPaginationView",

  data() {
    return {};
  },
  computed: {
    ...mapGetters("searchStore", ["pageCount", "startPageIndex", "endPageIndex", "prev", "next"]),
  },
  methods: {
    // 부모에게 event 전달
    paginationChanged(pageIndex) {
      console.log("paginationVue : paginationChanged : pageIndex : " + pageIndex);
      this.$emit("call-parent", pageIndex);
    },
  },
};
</script>

<style scoped>
.page-item {
  cursor: pointer;
}

.pagination.justify-content-center {
  margin-bottom: 9px !important;
}
</style>
