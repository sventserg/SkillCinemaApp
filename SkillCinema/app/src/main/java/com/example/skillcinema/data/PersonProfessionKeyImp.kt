package com.example.skillcinema.data

import android.content.Context
import com.example.skillcinema.R
import com.example.skillcinema.entity.PersonProfessionKey

enum class PersonProfessionKeyImp(override val key: String) : PersonProfessionKey {
    WRITER(PROFESSION_KEY_WRITER),
    OPERATOR(PROFESSION_KEY_OPERATOR),
    EDITOR(PROFESSION_KEY_EDITOR),
    COMPOSER(PROFESSION_KEY_COMPOSER),
    PRODUCER_USSR(PROFESSION_KEY_PRODUCER_USSR),
    HIMSELF(PROFESSION_KEY_HIMSELF),
    HERSELF(PROFESSION_KEY_HERSELF),
    HRONO_TITR_MALE(PROFESSION_KEY_HRONO_TITR_MALE),
    HRONO_TITR_FEMALE(PROFESSION_KEY_HRONO_TITR_FEMALE),
    TRANSLATOR(PROFESSION_KEY_TRANSLATOR),
    DIRECTOR(PROFESSION_KEY_DIRECTOR),
    DESIGN(PROFESSION_KEY_DESIGN),
    PRODUCER(PROFESSION_KEY_PRODUCER),
    ACTOR(PROFESSION_KEY_ACTOR),
    VOICE_DIRECTOR(PROFESSION_KEY_VOICE_DIRECTOR),
    UNKNOWN(PROFESSION_KEY_UNKNOWN)
}

class PersonProfessionKeyList {
    val personProfessionKeyList = listOf(
        PersonProfessionKeyImp.WRITER,
        PersonProfessionKeyImp.OPERATOR,
        PersonProfessionKeyImp.EDITOR,
        PersonProfessionKeyImp.COMPOSER,
        PersonProfessionKeyImp.PRODUCER_USSR,
        PersonProfessionKeyImp.HIMSELF,
        PersonProfessionKeyImp.HERSELF,
        PersonProfessionKeyImp.HRONO_TITR_MALE,
        PersonProfessionKeyImp.HRONO_TITR_FEMALE,
        PersonProfessionKeyImp.TRANSLATOR,
        PersonProfessionKeyImp.DIRECTOR,
        PersonProfessionKeyImp.DESIGN,
        PersonProfessionKeyImp.PRODUCER,
        PersonProfessionKeyImp.ACTOR,
        PersonProfessionKeyImp.VOICE_DIRECTOR,
        PersonProfessionKeyImp.UNKNOWN
    )
}

class GetPersonProfessionName {
    fun getPersonProfessionName(
        context: Context,
        personProfessionKey: String
    ): String {
        return when (personProfessionKey) {
            PersonProfessionKeyImp.WRITER.key -> context.getString(R.string.writer)
            PersonProfessionKeyImp.OPERATOR.key -> context.getString(R.string.operator)
            PersonProfessionKeyImp.EDITOR.key -> context.getString(R.string.editor)
            PersonProfessionKeyImp.COMPOSER.key -> context.getString(R.string.composer)
            PersonProfessionKeyImp.PRODUCER_USSR.key -> context.getString(R.string.producer_USSR)
            PersonProfessionKeyImp.HIMSELF.key -> context.getString(R.string.himself)
            PersonProfessionKeyImp.HERSELF.key -> context.getString(R.string.herself)
            PersonProfessionKeyImp.HRONO_TITR_MALE.key -> context.getString(R.string.hrono_titr_male)
            PersonProfessionKeyImp.HRONO_TITR_FEMALE.key -> context.getString(R.string.hrono_titr_female)
            PersonProfessionKeyImp.TRANSLATOR.key -> context.getString(R.string.translator)
            PersonProfessionKeyImp.DIRECTOR.key -> context.getString(R.string.director)
            PersonProfessionKeyImp.DESIGN.key -> context.getString(R.string.design)
            PersonProfessionKeyImp.PRODUCER.key -> context.getString(R.string.producer)
            PersonProfessionKeyImp.ACTOR.key -> context.getString(R.string.actor)
            PersonProfessionKeyImp.VOICE_DIRECTOR.key -> context.getString(R.string.voice_director)
            PersonProfessionKeyImp.UNKNOWN.key -> context.getString(R.string.unknown)
            else -> context.getString(R.string.unknown)
        }
    }
}