<template>
    <v-container fluid>
        <v-timeline v-if="!this.hidden" direction="horizontal" align-top>
            <v-timeline-item v-for="(timeLineItem, index) in TimelineData" :key="index" reverse>
                <v-card class="timeline-card d-flex flex-column">
                    <v-card-title class="flex-grow-1 timelinecard-title">
                        <v-icon>
                            mdi-pencil
                        </v-icon>
                        Bewerking
                    </v-card-title>
                    <v-card-text class="flex-grow-1 timelinecard-body">
                        <p> Author: {{ timeLineItem.firstname }} {{ timeLineItem.lastname }}</p>
                        <p> Bewerkt op: {{ formatDate(timeLineItem.date) }}</p>
                    </v-card-text>
                    <v-card-actions class="flex-grow-1 timelinecard-footer">
                        <v-btn color="red" @click="showDeleteAlert(timeLineItem)">
                            <v-icon>mdi-delete</v-icon>
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-dialog v-model="deleteDialog" max-width="900">
                            <v-card>
                                <v-card-title class="headline">Warning</v-card-title>
                                <v-card-text>
                                    Are you sure you want to delete: {{ selectedModalItem.name }}
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn @click="handleAction('delete', selectedModalItem.id)">OK</v-btn>
                                    <v-btn @click="handleAction('Cancel', selectedModalItem.id)">Cancel</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-btn color="blue" @click="openModal(timeLineItem)">
                            <v-icon>mdi-eye</v-icon>
                        </v-btn>
                        <v-dialog v-model="modalOpen" full-screen>
                            <v-card class="article-modal">
                                <v-row>
                                    <v-col cols="12">
                                        <p class="text-h6">Bewerkinginfo</p>
                                        <p class="text-h8">Naam: {{ selectedModalItem.firstname }} - {{ selectedModalItem.lastname }}</p>
                                        <p class="text-h8">Bewerking gedaan op {{ formatDate(selectedModalItem.date) }}</p>
                                    </v-col>
                                </v-row>
                                <div class="formatted-xml">
                                    <v-row>
                                        <v-col cols="6" md="6">
                                            <h2 class="text-h6">Aanpassingen Artikel</h2>
                                            <ModalArticleComponent :articles="articleData.articles" :changes="findChangesInTimelineData(timelineData, articleData.articles)" ></ModalArticleComponent>
                                        </v-col>
                                        <v-col cols="6" md="6">
                                            <h2 class="text-h6">Origineel Artikel</h2>
                                            <ModalArticleComponent :articles="articleData.articles"></ModalArticleComponent>
                                        </v-col>
                                    </v-row>
                                    <v-row>
                                        <v-col cols="12">
                                            <v-card-actions>
                                                <v-btn color="black" class="ma-2" @click="closeModal">
                                                    <v-icon start icon="mdi-minus-circle">
                                                    </v-icon>
                                                    Close</v-btn>
                                            </v-card-actions>
                                        </v-col>
                                    </v-row>
                                </div>
                            </v-card>
                        </v-dialog>
                    </v-card-actions>
                </v-card>
            </v-timeline-item>
        </v-timeline>
        <v-row v-if="timelineData && timelineData.length > 0">
            <v-col class="timeline-navbar d-flex justify-end">
                <v-btn color="primary" @click="prev" :disabled="currentIndex === 0">Previous</v-btn>
                <v-btn color="primary" @click="next" :disabled="currentIndex >= timelineData.length - 3">Next</v-btn>
                <v-btn color="primary" @click="hide">Hide</v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>
<style scoped>
.timeline-card {
    width: 300px;
    height: 250px;
}

.timelinecard-title {
    background-color: #263238;
    color: white;
}

.timelinecard-footer {
    background-color: #0D47A1;
    align-items: center;

}

.timelinecard-body {
    background-color: #F5F5F5;
    text-align: center;
}


/* .article-modal {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
} */
</style>
<script>
import axios from "axios";
import ModalArticleComponent from "./ModalArticleComponent.vue";
export default {
    components: { ModalArticleComponent },
    data() {
        return {
            modalOpen: false,
            deleteDialog: false,
            selectedModalItem: {},
            currentIndex: 0,
            hidden: false,
            changesData: [],
        };
    },
    props: {
        timelineData: {
            type: Array,
            required: true
        },
        articleData: {
            type: Object,
            required: true,
        },
        colorOptions: {
            type: Array,
            required: true,
        }
    },
    computed: {
        TimelineData() {
            this.timelineData.map(item => {
                item.date = new Date(item.date);
                return item;
            })
            return this.timelineData.slice(this.currentIndex, this.currentIndex + 3)
        }
        
    },
    methods: {

        findChangesInTimelineData(timeLineData){
            timeLineData.filter((obj, index, self) =>
                index === self.findIndex((t) => (t.woord === obj.woord))
            )
            timeLineData.forEach(item => {
                const match = this.selectedModalItem.date == item.date;
                if (match){
                    const updatedItem = {
                        ...item,
                        color: true,

                    }
                    this.changesData.push(updatedItem)
                }
            });
            console.log(this.changesData)
            return this.changesData;
        },

        formatDate(date) {
            return date.toLocaleDateString('en-Us', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                hour12: false
            });
        },
        openModal(item) {
            this.modalOpen = true;
            this.selectedModalItem = item;
        },
        //role check needs to be added
        showDeleteAlert(item) {
            this.deleteDialog = true;
            this.selectedModalItem = item;
        },
        closeModal() {
            this.modalOpen = false;
        },
        //role check needs to be added
        async handleAction(action, id) {
            if (action == "delete") {
                try {
                    await axios.delete(`http://localhost:8085/XMLbron/${id}`)
                } catch (error) {
                    console.error()
                }

                this.deleteDialog = false;
            }
            this.deleteDialog = false;
        },
        deleteArticle() {
            return null;
        },
        editArticle() {
            return null;
        },
        hide() {
            this.hidden = !this.hidden;
            console.log(this.hidden)
        },
        next() {
            if (this.currentIndex < this.timelineData.length - 3) {
                this.currentIndex += 3;
            }
        },
        prev() {
            if (this.currentIndex > 0) {
                this.currentIndex -= 3;
            }
        }
    }
};
</script>
