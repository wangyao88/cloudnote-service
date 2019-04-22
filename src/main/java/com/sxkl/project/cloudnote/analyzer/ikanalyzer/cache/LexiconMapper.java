package com.sxkl.project.cloudnote.analyzer.ikanalyzer.cache;

import org.apache.ibatis.annotations.*;

import java.text.MessageFormat;
import java.util.List;

@Mapper
public interface LexiconMapper {

    @Insert("INSERT INTO cn_lexicon(id, name, discriminator) VALUES (#{id}, #{lexicon}, #{key})")
    void addLexicon(@Param("id") String id, @Param("key") String key, @Param("lexicon") String lexicon);

    @Select("SELECT NAME FROM cn_lexicon WHERE DISCRIMINATOR=#{key}")
    List<String> getLexicons(@Param("key") String key);

    @Select("SELECT NAME FROM cn_lexicon WHERE DISCRIMINATOR=#{key} and name like #{search}")
    List<String> getLexiconsForSearch(@Param("key") String key, @Param("search") String search);

    @Delete("DELETE FROM cn_lexicon WHERE NAME=#{lexicon}")
    void deleteLexicon(@Param("lexicon") String lexicon);

    @Delete("DELETE FROM cn_lexicon")
    void deleteAll();

    @InsertProvider(type = LexiconMapperProvider.class, method = "batchAddLexicon")
    int batchAddLexicon(@Param("lexicons") List<Lexicon> lexicons);

    class LexiconMapperProvider {

        public String batchAddLexicon(@Param("lexicons") List<Lexicon> lexicons) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO cn_lexicon(id, name, discriminator) VALUES ");
            MessageFormat mf = new MessageFormat("(#'{'lexicons[{0}].id}, #'{'lexicons[{0}].name}, #'{'lexicons[{0}].discriminator})");
            int size = lexicons.size();
            int limit = size - 1;
            for (int i = 0; i < size; i++) {
                sql.append(mf.format(new Object[]{i}));
                if (i < limit) {
                    sql.append(",");
                }
            }
            return sql.toString();
        }
    }
}
