package com.example.qltvkotlin.presentation.feature.adddocgia

import android.graphics.Bitmap
import android.net.Uri
import java.util.Date

interface IComponent

interface EditableField : IComponent, HasLabel, HasValue
interface PhotoField : IComponent, HasImage
interface DividerComponent : IComponent
interface DateField : IComponent, IChars, HasLabel

interface HasLabel {
    val label: String
}

interface HasImage {
    val image: Image
}

interface Image
interface HasUri {
    val uri: Uri
}

interface HasValue {
    val value: String
}

object EmptyImage : Image
interface HasBitmap {
    val bitmap: Bitmap
}

interface IChars : CharSequence {
    override val length: Int
        get() = toString().length

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return toString().subSequence(startIndex, endIndex)
    }

    override fun get(index: Int): Char {
        return toString()[index]
    }

    override fun toString(): String
}

interface HasDate {
    val date: Date
}

object EmptyDate : Date()