package me.imtoggle.barhud.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.gui.animations.Animation
import cc.polyfrost.oneconfig.gui.animations.DummyAnimation
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UGraphics.GL
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.utils.dsl.*
import me.imtoggle.barhud.mixin.GuiAccessor
import me.imtoggle.barhud.mixin.GuiIngameAccessor
import me.imtoggle.barhud.mixin.GuiSpectatorAccessor
import me.imtoggle.barhud.utils.Linear
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.gui.spectator.SpectatorMenu
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.entity.player.EntityPlayer

object HotBar : SubConfig("HotBar", "barhud/hotbar.json") {

    @HUD(name = "Hud")
    var hud = HotBarHud()

    init {
        initialize()
    }


    class HotBarHud : BasicHud(true, 1920 / 2f - 182 / 2f, 1080 - 22f, 1f, true, true, 4f, 0f, 0f, OneColor(0, 0, 0, 120), false, 2f, OneColor(0, 0, 0)) {

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
                if (mc.renderViewEntity is EntityPlayer) {
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
                    mc.textureManager.bindTexture(accessor.widgetsTexPath)
                    val entityPlayer = mc.renderViewEntity as EntityPlayer
                    GlStateManager.enableRescaleNormal()
                    GlStateManager.enableBlend()
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
                    RenderHelper.enableGUIStandardItemLighting()

                    for (j in 0..8) {
                        val item = entityPlayer.inventory.mainInventory[j] ?: continue
                        val itemRenderer = mc.renderItem
                        GlStateManager.pushMatrix()
                        GlStateManager.translate(x + (j * 20 + 3) * scale, y + 3 * scale, 0f)
                        GlStateManager.scale(scale, scale, 1f)
                        itemRenderer.renderItemAndEffectIntoGUI(item, 0, 0)
                        itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, item, 0, 0, null)
                        GlStateManager.enableAlpha()
                        GlStateManager.popMatrix()
                    }

                    RenderHelper.disableStandardItemLighting()
                    GlStateManager.disableRescaleNormal()
                    GlStateManager.disableBlend()
                }
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
                val g = specAccessor.alpha()
                if (g <= 0.0f) {
                    menu.func_178641_d()
                } else {
                    val h = guiAccessor.zLevel
                    guiAccessor.zLevel = -90.0f
                    val spectatorDetails = menu.func_178646_f()
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
                    GlStateManager.enableRescaleNormal()
                    GlStateManager.enableBlend()
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
                    GlStateManager.color(1.0f, 1.0f, 1.0f, g)
                    mc.textureManager.bindTexture(specAccessor.field_175267_f)
                    RenderHelper.enableGUIStandardItemLighting()
                    for (j in 0..8) {
                        specAccessor.drawItem(j, (x / hud.getScale() + j * 20 + 3).toInt(), y / scale + 3.0f, 1.0f, spectatorDetails.func_178680_a(j))
                    }
                    RenderHelper.disableStandardItemLighting()
                    GlStateManager.disableRescaleNormal()
                    GlStateManager.disableBlend()
                    GL.popMatrix()
                    guiAccessor.zLevel = h
                }
            }
        }

        private fun checkSlotChange(slot: Int) {
            if (slot != this.slot) {
                slotAnimation = Linear(duration, slotAnimation.get(), slot.toFloat(), false)
                this.slot = slot
            }
        }

        override fun shouldShow(): Boolean {
            val accessor = mc.ingameGUI.spectatorGui as GuiSpectatorAccessor
            return super.shouldShow() && (!mc.thePlayer.isSpectator || accessor.alpha() > 0f)
        }
    }
}