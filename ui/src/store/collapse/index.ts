import { defineStore } from 'pinia'


export const collapseStore = defineStore('isCollapse', {
    state: () => {
        return {
            isCollapse: false
        }
    },
    getters: {
        getCollapse(state) {
            return state.isCollapse
        }
    },
    actions: {
        toggleCollapse() {
            this.isCollapse = !this.isCollapse
        }
    }
})