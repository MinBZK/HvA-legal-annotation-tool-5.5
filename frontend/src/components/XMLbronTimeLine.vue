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
                        <v-dialog v-model="deleteDialog" max-width="400">
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
                        <v-dialog v-model="modalOpen" max-width="600">
                            <v-card>
                                <v-card-title>
                                    {{ selectedModalItem.artikelNaam }}
                                </v-card-title>
                                <v-card-subtitle>
                                    <p>Naam: {{ selectedModalItem.firstname }} {{ selectedModalItem.lastname }}</p>
                                    <p>Artikel datum: {{ selectedModalItem.xmlbronDate }}</p>
                                    <p>Bewerkt op: {{ formatDate(selectedModalItem.date) }}</p>
                                </v-card-subtitle>
                                <v-card-text>
                                    Full text description
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn color="black" class="ma-2" @click="closeModal">
                                        <v-icon start icon="mdi-minus-circle">
                                        </v-icon>
                                        Close</v-btn>
                                    <v-btn color="blue" class="ma-2">
                                        <v-icon start icon="mdi-pencil">
                                        </v-icon>Edit</v-btn>
                                </v-card-actions>
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

.timeline-navbar {

}
</style>
<script>
import axios from "axios";
export default {
    data() {
        return {
            modalOpen: false,
            deleteDialog: false,
            selectedModalItem: {},
            currentIndex: 0,
            hidden: false
        };
    },
    props: {
        timelineData: {
            type: Array,
            required: true
        },
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
        formatDate(date){
            return date.toLocaleDateString('en-Us',{
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
            console.log(item)
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
        hide(){
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