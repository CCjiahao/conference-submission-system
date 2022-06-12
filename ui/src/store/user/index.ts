import { defineStore } from 'pinia'


export const userStore = defineStore('user', {
    state: () => {
        return {
            username: null,
            name: null,
            email: null,
            school: null,
            country: null,
            expertise: null,
            role: null
        }
    },
    getters: {
        getState(state) {
            return state
        },
        isNull(state) {
            return state.username == null;
        }
    },
    actions: {
        setState(user: any) {
            this.username = user.username
            this.name = user.name
            this.email = user.email
            this.school = user.school
            this.country = user.country
            this.expertise = user.expertise
            this.role = user.role
        },
        clear() {
            this.username = null
            this.name = null
            this.email = null
            this.school = null
            this.country = null
            this.expertise = null
            this.role = null
        }
    }
})