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
                        <v-btn>
                            Delete
                        </v-btn>
                        <v-btn @click="openModal">
                            Preview
                        </v-btn>
                        <v-dialog v-model="modalOpen" max-width="600">
                            <v-card>
                                <v-card-title>
                                    {{ item.name }}
                                </v-card-title>
                                <v-card-text>
                                    <!-- Modal content goes here -->
                                    This is the content of the modal.
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn @click="closeModal">Close</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-btn>
                            Set
                        </v-btn>
                    </v-card-actions>
                </v-card> </v-timeline-item>
        </v-timeline>
    </v-container>>
</template>
<style scoped></style>
<script>

import { xmlBronStore } from "@/store/xmlbron"
export default {
    props: {
        timelineData: Array, // Assuming you pass timelineData as a prop
    },
    data() {
        return {
            modalOpen: false,
        };
    },
    methods: {
        openModal() {
            this.modalOpen = true;
        },
        closeModal() {
            this.modalOpen = false;
        }
    }
};
</script>