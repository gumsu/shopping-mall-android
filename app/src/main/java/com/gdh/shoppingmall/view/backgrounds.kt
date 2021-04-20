package com.gdh.shoppingmall.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable

/**
 * 상하좌우에 선이 있는 Drawable 객체를 생성하고 반환하는 코드
 *
 * ColorDrawable -> 사각형 영역을 특정 색으로 채워주는 Drawable 클래스
 *
 * .let(::LayerDrawable) -> val layerDrawble = LayerDrawable(layerDrawable)
 * layerDrawable -> 층층이 겹쳐서 그려주는 역할, setLayerInset로 바깥쪽 여백을 지정하는 방법으로 선을 그려준다.
 **/

private fun borderBG(
    borderColor: String = "#1F000000",
    bgColor: String = "#FFFFFF",
    borderLeftWidth: Int = 0,
    borderTopWidth: Int = 0,
    borderRightWidth: Int = 0,
    borderBottomWidth: Int = 0
): LayerDrawable {
    val layerDrawable = arrayOf<Drawable>(
        ColorDrawable(Color.parseColor(borderColor)),
        ColorDrawable(Color.parseColor(bgColor))
    ).let(::LayerDrawable)

    layerDrawable.setLayerInset(
        1,
        borderLeftWidth,
        borderTopWidth,
        borderRightWidth,
        borderBottomWidth
    )
    return layerDrawable
}

fun borderLeft(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderLeftWidth = width
)

fun borderTop(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderTopWidth = width
)

fun borderRight(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderRightWidth = width
)

fun borderBottom(
    color: String = "#1F000000",
    width: Int
) = borderBG(
    borderColor = color,
    borderBottomWidth = width
)