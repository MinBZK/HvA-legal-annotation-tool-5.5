import { shallowMount } from "@vue/test-utils"
import { it } from "vuetify/lib/locale/index.mjs"
import { XMLbronTimeLine } from "@/components/XMLbronTimeLine.vue"

describe('XMLbronTimeLine.vue' , () => {
    it('calls showDeleteAlert when delete button is clicked', async () => {
        const wrapper = shallowMount(XMLbronTimeLine, {
            propsData: {
                
            }
        })

        wrapper.vm.showDeleteAlert = jest.fn()
        await wrapper.find('.delete-button').trigger('click')

        expect(wrapper.vm.showDeleteAlert).toHaveBeenCalled();
    })

})

