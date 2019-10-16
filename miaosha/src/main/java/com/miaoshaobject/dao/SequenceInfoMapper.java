package com.miaoshaobject.dao;

import com.miaoshaobject.dataobject.SequenceInfo;

public interface SequenceInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated
     */
    int insert(SequenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated
     */
    int insertSelective(SequenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated
     */
    SequenceInfo selectByPrimaryKey(String name);
    SequenceInfo getSequenceByName(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SequenceInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SequenceInfo record);
}