


ForkJoinTask<List<AgreementCmsMappingOndId>> futureAllAgreementCmsMappingData = forkJoinPool.submit(() -> MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getALLAgreementCmsMappingData());
        ForkJoinTask<List<AgreementManageOneId>> futureAgreementManageByAgreementOn =forkJoinPool.submit(() ->  MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getAllAgreementManageByAgreementOn());
        ForkJoinTask<List<UserOrganizationInfoOneId>> futureUserOrganization =forkJoinPool.submit(() ->  MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getAllUserOrganization());
        ForkJoinTask<List<OrganizationInfoOneId>> futureAllOrganizationInfo =forkJoinPool.submit(() ->  MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getALLOrganizationInfo());
        ForkJoinTask<List<UserInfoOneId>> futureUserInfoAll =forkJoinPool.submit(() ->  MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getUserInfoAll());
        ForkJoinTask<List<UserInfoOneId>> futureOrgNameByUserId =forkJoinPool.submit(() ->  MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getOrgNameByUserId());
        ForkJoinTask<List<AgreementDTOOneId>> futureAllAgreement = forkJoinPool.submit(() -> MEMBER_BASE_INFO_TRANSFORMER_DAO_ONE_ID.getAllAgreement());
