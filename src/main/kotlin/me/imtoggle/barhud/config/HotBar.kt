package me.imtoggle.barhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.*
import cc.polyfrost.oneconfig.gui.animations.*
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UGraphics.GL
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.mixin.*
import me.imtoggle.barhud.utils.Linear
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.gui.spectator.SpectatorMenu
import net.minecraft.client.gui.spectator.categories.SpectatorDetails
import net.minecraftforge.client.GuiIngameForge

object HotBar : Config(Mod("HotBar", ModType.HUD), "barhud/hotbar.json") {

    @HUD(name = "Hud")
    var hud = HotBarHud()

    init {
        initialize()
    }


    class HotBarHud : BasicHud() {

        @Slider(
            name = "Animation Duration",
            min = 0f, max = 500f
        )
        var duration = 200f

        @Color(
            name = "Current Slot Color"
        )
        var normalColor = OneColor(0, 0, 0, 63)

        @Transient
        var slot = 0

        @Transient
        var slotAnimation: Animation = DummyAnimation(1f)

        override fun drawAll(matrices: UMatrixStack?, example: Boolean) {
            super.drawAll(matrices, example)
        }

        override fun isEnabled(): Boolean {
            val b = super.isEnabled()
            GuiIngameForge.renderHotbar = !b;
            return b;
        }

        override fun draw(matrices: UMatrixStack?, x: Float, y: Float, scale: Float, example: Boolean) {
            val scaledResolution = ScaledResolution(mc)
            if (mc.playerController.isSpectator) {
                val accessor: GuiSpectatorAccessor = mc.ingameGUI.spectatorGui as GuiSpectatorAccessor
                val guiAccessor: GuiAccessor = mc.ingameGUI.spectatorGui as GuiAccessor
                drawSpec(x, y, accessor.field_175271_i, accessor, guiAccessor, scaledResolution)
            } else {
                val accessor: GuiIngameAccessor = mc.ingameGUI as GuiIngameAccessor
                if (mc.thePlayer.inventory.currentItem >= 0) {
                    checkSlotChange(mc.thePlayer.inventory.currentItem)
                    nanoVG(true) {
                        translate(x + slotAnimation.get() * 20 * scale, y)
                        scale(scale, scale)
                        drawRoundedRect(0f, 0f, 22, 22, cornerRadius, normalColor.rgb)
                    }
                }
                accessor.renderHotBar(scaledResolution, 0f)
            }
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 182f * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 22f * scale
        }

        private fun drawSpec(x: Float, y: Float, menu: SpectatorMenu?, specAccessor: GuiSpectatorAccessor, guiAccessor: GuiAccessor, scaledResolution: ScaledResolution) {
            if (menu != null) {
                val g: Float = specAccessor.alpha()
                if (g <= 0.0f) {
                    menu.func_178641_d()
                } else {
                    val h: Float = guiAccessor.zLevel
                    guiAccessor.zLevel = -90.0f
                    val spectatorDetails: SpectatorDetails = menu.func_178646_f()
                    if (spectatorDetails.func_178681_b() >= 0) {
                        checkSlotChange(spectatorDetails.func_178681_b())
                        nanoVG(true) {
                            translate(x + slotAnimation.get() * 20 * scale, y)
                            scale(scale, scale)
                            drawRoundedRect(0f, 0f, 22, 22, cornerRadius, normalColor.rgb)
                        }
                    }
                    GL.pushMatrix()
                    GL.scale(scale, scale, scale)
                    specAccessor.draw(scaledResolution, g, (x + 91).toInt(), y / scale, spectatorDetails)
                    GL.popMatrix()
                    guiAccessor.zLevel = h
                }
            }
        }

        private fun checkSlotChange(slot: Int) {
            if (slot != this.slot) {
                slotAnimation = Linear(duration, this.slot.toFloat(), slot.toFloat(), false)
                this.slot = slot
            }
        }

        override fun shouldShow(): Boolean {
            val accessor: GuiSpectatorAccessor = mc.ingameGUI.spectatorGui as GuiSpectatorAccessor
            return super.shouldShow() && (!mc.thePlayer.isSpectator || accessor.alpha() > 0f)
        }
    }
}