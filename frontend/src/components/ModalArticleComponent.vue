<template>
    <div class="formatted-xml">
        <div v-for="article in articles" :key="article.number">
            <h3>{{ article.title }}</h3>
            <ol>
                <li v-for="(part, partIndex) in article.parts" :key="partIndex">
                    <div>
                        <span v-for="(word, wordIndex) in part.partWords" :key="wordIndex"> 
                            {{ processWordsToSpaceWords(word.name) }}
                            </span>

                            <span>

                                <ul>
                                    <li v-for="(subPart, subPartIndex) in part.subParts" :key="subPartIndex">
                                        <span> {{ processWordsToSpaceWords(subPart.number) }} </span>

                                        <span v-for="(word, wordIndex) in subPart.subPartWords" :key="wordIndex">
                                            {{ processWordsToSpaceWords(word.name) }}
                                        </span>

                                    </li>
                                </ul>
                        </span>
                    </div>
                </li>
            </ol>
        </div>
    </div>
</template>
<style>
.formatted-xml {
    margin: 0;
    /* Remove default margin */
    line-height: 1.5;
    /* Adjust line height for better readability */
}

.formatted-xml h3 {
    font-size: 20px;
    margin-bottom: 5px;
}

.formatted-xml ol {
    font-size: 15px;
    list-style-type: decimal;
    margin-bottom: 10px;
}

.formatted-xml li {
    margin-bottom: 5px;
}

.formatted-xml ul li {
    /* Remove the asterisk for subparts */
    list-style-type: none;
}
</style>
<script>

export default {
    props: {
        articles: {
            type: Array,
            required: true,
        },
        changes: {
            type: Array,
            required: false,
        },
    },
    methods: {
        processWordsToSpaceWords(text){
            return text + ' '
        }
    }
}

</script>