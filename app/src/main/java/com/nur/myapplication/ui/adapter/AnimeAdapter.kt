package com.nur.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nur.myapplication.databinding.ItemAnimeBinding
import com.nur.myapplication.models.AnimeUI

/**
 * Адаптер для отображения списка аниме в RecyclerView.
 *
 * @constructor Создает экземпляр адаптера.
 */
class AnimeAdapter : ListAdapter<AnimeUI, AnimeAdapter.AnimeViewHolder>(diffUtil) {

    /**
     * ViewHolder для элемента аниме.
     *
     * @property binding Привязка для элемента аниме.
     */
    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Привязывает данные аниме к элементам пользовательского интерфейса.
         *
         * @param item Объект [AnimeUI], содержащий данные аниме.
         */
        fun onBind(item: AnimeUI) {
            Glide.with(binding.ivImage.context)
                .load(item.attributes.posterImage.original)
                .into(binding.ivImage)
            binding.tvName.text = item.attributes.titles.enJp
        }
    }

    /**
     * Создает новый ViewHolder для элемента аниме.
     *
     * @param parent Родительский ViewGroup, в который будет добавлен элемент.
     * @param viewType Тип представления (не используется в данном адаптере).
     * @return Новый экземпляр [AnimeViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Привязывает данные к указанному ViewHolder.
     *
     * @param holder [AnimeViewHolder], к которому будут привязаны данные.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        getItem(position).let {
            holder.onBind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AnimeUI>() {
            /**
             * Определяет, являются ли элементы одинаковыми.
             *
             * @param oldItem Старый элемент списка.
             * @param newItem Новый элемент списка.
             * @return true, если элементы одинаковые, иначе false.
             */
            override fun areItemsTheSame(
                oldItem: AnimeUI,
                newItem: AnimeUI
            ): Boolean {
                return oldItem.id == newItem.id
            }

            /**
             * Определяет, равны ли содержимое двух элементов.
             *
             * @param oldItem Старый элемент списка.
             * @param newItem Новый элемент списка.
             * @return true, если содержимое одинаковое, иначе false.
             */
            override fun areContentsTheSame(
                oldItem: AnimeUI, newItem: AnimeUI
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
