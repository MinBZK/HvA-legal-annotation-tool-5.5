<template>
    <v-container fluid>
        <v-timeline direction="horizontal">
            <v-timeline-item v-for="(item, index) in timelineData" :key="index">
                <v-card>
                    <v-card-title>
                        {{ item.name }}
                    </v-card-title>
                    <v-card-text class="bg-white text--primary">
                        <p> Author: </p>
                        <p> {{ item.date }}</p>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn @click="showDeleteAlert(item)">
                            Delete
                        </v-btn>
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
                        <v-btn @click="openModal(item)">
                            Preview
                        </v-btn>
                        <v-dialog v-model="modalOpen" max-width="600">
                            <v-card>
                                <v-card-title>
                                    {{ selectedModalItem.name }}
                                </v-card-title>
                                <v-card-text>
                                    Full text description
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn @click="closeModal">Close</v-btn>
                                    <v-btn>Edit</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-card-actions>
                </v-card>
            </v-timeline-item>
        </v-timeline>
    </v-container>>
</template>
<style scoped></style>
<script>

import { xmlBronStore } from "@/store/xmlbron"
import axios from "axios";
export default {
    props: {
        timelineData: Array,
    },
    data() {
        return {
            modalOpen: false,
            deleteDialog: false,
            selectedModalItem: {},
        };
    },
    methods: {
        openModal(item) {
            this.modalOpen = true;
            this.selectedModalItem = item;
            console.log(item)
        },
        //role check needs to be added
        showDeleteAlert(item){
            this.deleteDialog = true;
            this.selectedModalItem = item;
        },
        closeModal() {
            this.modalOpen = false;
        },
        //role check needs to be added
        async handleAction(action, id){
            if(action == "delete"){
                try {
                    await axios.delete(`http://localhost:8085/XMLbron/${id}`)
                } catch(error){
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
        }
    }
};
</script>